package kr.ac.snu.tport.domain.path

import kr.ac.snu.tport.domain.bus.BusService
import kr.ac.snu.tport.domain.path.dto.PathDetail
import kr.ac.snu.tport.domain.path.dto.PathDetailBuilder
import kr.ac.snu.tport.domain.path.model.PathRepository
import kr.ac.snu.tport.domain.reservation.ReservationService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class PathService(
    private val pathRepository: PathRepository,
    private val busService: BusService,
    private val reservationService: ReservationService
) {

    suspend fun search(
        originName: String,
        destinationName: String,
        departureTime: LocalDateTime
    ): List<PathDetail> {
        val paths = searchPaths(originName, destinationName, departureTime)
        val buses = paths.map { it.bus }.distinct()
        val reservationsMap = reservationService.getReservations(buses, departureTime)
        return paths.map {
            PathDetailBuilder.build(
                it,
                reservationsMap[it.bus].orEmpty(),
                departureTime
            )
        }
    }

    private suspend fun searchPaths(
        originName: String,
        destinationName: String,
        departureTime: LocalDateTime
    ): List<Path> {
        val departureDate = departureTime.toLocalDate()
        val availablePaths = pathRepository.findAllByGetOnBusStopAndGetOffBusStop(
            getOnBusStop = originName,
            getOffBusStop = destinationName
        )

        val busMap = busService.findAll(availablePaths.map { it.busId }.distinct())
            .associateBy { it.busId }

        return availablePaths
            .associateWith { busMap[it.busId]!! }
            .toList()
            .asSequence()
            .filter { (path, bus) ->
                val originArrivalTime = departureDate.atTime(bus.findArrivalTimeOf(path.getOnBusStop) ?: LocalTime.MIN)
                originArrivalTime >= departureTime
            }
            .sortedBy { (path, bus) ->
                bus.findArrivalTimeOf(path.getOffBusStop) ?: LocalTime.MAX
            }
            .take(10)
            .map { (path, bus) -> path.toDomain(bus) }
            .toList()
    }
}
