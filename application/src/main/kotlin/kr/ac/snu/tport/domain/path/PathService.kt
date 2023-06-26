package kr.ac.snu.tport.domain.path

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusService
import kr.ac.snu.tport.domain.bus.NORMAL_BUS
import kr.ac.snu.tport.domain.bus.Vehicle
import kr.ac.snu.tport.domain.bus.WALK
import kr.ac.snu.tport.domain.path.dto.PathGroupDetail
import kr.ac.snu.tport.domain.path.dto.PathGroupDetailBuilder
import kr.ac.snu.tport.domain.path.model.PathGroupEntity
import kr.ac.snu.tport.domain.path.model.PathGroupRepository
import kr.ac.snu.tport.domain.path.model.SubPathsEntity
import kr.ac.snu.tport.domain.path.model.SubPathsRepository
import kr.ac.snu.tport.domain.reservation.ReservationService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class PathService(
    private val pathGroupRepository: PathGroupRepository,
    private val subPathsRepository: SubPathsRepository,
    private val busService: BusService,
    private val reservationService: ReservationService
) {
    suspend fun search(pathGroupId: Long, departureTime: LocalDateTime): PathGroupDetail {
        val pathGroup = pathGroupRepository.findById(pathGroupId)!!
        val subPaths = subPathsRepository.findAllByPathGroupIdIn(pathGroupIds = listOf(pathGroup.id!!))
        val bus = busService.findAll(subPaths.mapNotNull { it.busId }).distinct().associateBy { it.busId }

        val pathGroupDomain = pathGroup.toDomain(subPaths.associateWith { bus[it.busId] })
        val metroSubPath = pathGroupDomain.subPaths.first { it.vehicle is Bus }
        val metroSubPathBus = metroSubPath.vehicle as Bus
        val reservationsMap = reservationService.getReservations(listOf(metroSubPathBus), departureTime)

        return PathGroupDetailBuilder.build(
            pathGroupDomain,
            metroSubPath, metroSubPathBus,
            reservationsMap[metroSubPathBus].orEmpty(),
            departureTime
        )
    }

    suspend fun search(
        originName: String,
        destinationName: String,
        departureTime: LocalDateTime
    ): List<PathGroupDetail> {
        val pathGroups = searchPaths(originName, destinationName, departureTime)
        val buses = pathGroups.flatMap { it.subPaths.mapNotNull { (it.vehicle as? Bus) } }.distinct()
        val reservationsMap = reservationService.getReservations(buses, departureTime)
        return pathGroups.map {
            val metroSubPath = it.subPaths.first { it.vehicle is Bus }
            val metroSubPathBus = metroSubPath.vehicle as Bus
            PathGroupDetailBuilder.build(
                it,
                metroSubPath, metroSubPathBus,
                reservationsMap[metroSubPathBus].orEmpty(),
                departureTime
            )
        }
    }

    private suspend fun searchPaths(
        originName: String,
        destinationName: String,
        departureTime: LocalDateTime
    ): List<PathGroup> {
        val departureDate = departureTime.toLocalDate()
        val availablePathEntities = pathGroupRepository.findAllByGetOnBusStopAndGetOffBusStop(
            getOnBusStop = originName,
            getOffBusStop = destinationName
        )
            .sortedBy {
                // 가장 가까운 출발시각
                val todayBusDepartureTime =
                    departureDate.atTime(LocalTime.parse(it.startTime))
                // 출발시각이 아직 안 지난 경로는 당일 출발시각
                // 출발시각이 지난 경로는 다음날 출발시각으로 변경
                val latestDepartureTime =
                    if (todayBusDepartureTime > departureTime) todayBusDepartureTime
                    else todayBusDepartureTime.plusDays(1)

                // 가장 가까운 출발시각 + 경로의 총 소요시간 = 도착시간
                // 도착시간이 빠른 경로부터 정렬
                latestDepartureTime.plusMinutes(it.travelTime.toLong())
            }
            .take(10)

        val subPaths = subPathsRepository.findAllByPathGroupIdIn(
            pathGroupIds = availablePathEntities.map { it.id!! }.distinct()
        ).groupBy { it.pathGroupId }

        val buses = busService.findAll(
            busIds = subPaths.values.flatten().mapNotNull { it.busId }
        ).associateBy { it.busId }

        return availablePathEntities.map { pathGroup ->
            pathGroup
                .toDomain(subPaths = subPaths[pathGroup.id!!].orEmpty().associateWith { buses[it.busId] })
        }.toList()
    }

    private fun PathGroupEntity.toDomain(
        subPaths: Map<SubPathsEntity, Bus?>
    ): PathGroup {
        return PathGroup(
            id = id!!,
            getOnBusStop = getOnBusStop,
            getOffBusStop = getOffBusStop,
            fare = fare,
            travelTime = travelTime,
            startTime = LocalTime.parse(startTime),
            subPaths = subPaths.map { (it, bus) ->
                val vehicleType = Vehicle.Type.values()
                    .find { enum -> enum.name == it.type }

                val vehicle = when (vehicleType) {
                    Vehicle.Type.WALK -> WALK
                    Vehicle.Type.NORMAL_BUS -> NORMAL_BUS
                    Vehicle.Type.M_BUS -> bus!!
                    else -> throw IllegalArgumentException("Unknown vehicle type: $vehicleType")
                }

                PathGroup.SubPaths(
                    getOnBusStop = it.getOnBusStop,
                    getOffBusStop = it.getOffBusStop,
                    travelTime = it.travelTime,
                    vehicle = vehicle
                )
            }
        )
    }
}
