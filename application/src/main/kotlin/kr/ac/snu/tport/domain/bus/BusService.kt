package kr.ac.snu.tport.domain.bus

import kotlinx.coroutines.flow.toList
import kr.ac.snu.tport.domain.bus.model.BusRepository
import kr.ac.snu.tport.domain.bus.model.BusStopRepository
import org.springframework.stereotype.Service
import java.time.LocalTime

interface BusService {
    suspend fun findAll(busIds: List<Long>): List<Bus>
}

@Service
class BusServiceImpl(
    private val busRepository: BusRepository,
    private val busStopRepository: BusStopRepository
) : BusService {

    override suspend fun findAll(busIds: List<Long>): List<Bus> {
        val busEntities = busRepository.findAllByIdIn(busIds).toList()
        val busStopArrivalEntities = busStopRepository.findAllByBusIdIn(busEntities.mapNotNull { it.id })
            .toList()
            .groupBy { it.busId }

        return busEntities.map {
            Bus(
                busId = it.id!!,
                busNum = it.busNum,
                departureTime = LocalTime.parse(it.departureTime),
                busStop = busStopArrivalEntities[it.id]!!
                    .map { busStopArrivalEntity ->
                        BusStop(
                            name = busStopArrivalEntity.busStopName,
                            busArrivalTime = LocalTime.parse(busStopArrivalEntity.arrivalTime)
                        )
                    }
            )
        }
    }
}
