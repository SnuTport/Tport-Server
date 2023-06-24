package kr.ac.snu.tport.domain.path

import kr.ac.snu.tport.domain.bus.BusService
import kr.ac.snu.tport.domain.path.model.PathRepository
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class PathService(
    private val busService: BusService,
    private val pathRepository: PathRepository
) {

    suspend fun search(
        originName: String,
        destinationName: String,
        departureTime: LocalTime
    ): List<Path> {
        val availablePaths = pathRepository.findAllByGetOnBusStopAndGetOffBusStop(
            getOnBusStop = originName,
            getOffBusStop = destinationName
        )

        val busMap =
            busService.findAll(availablePaths.map { it.busId }.distinct()).associateBy { it.busId }

        return availablePaths
            .associateWith { busMap[it.busId]!! }
            .toList()
            .asSequence()
            .filter { (path, bus) ->
                val originArrivalTime = bus.findArrivalTimeOf(path.getOnBusStop) ?: LocalTime.MIN
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
