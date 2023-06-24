package kr.ac.snu.tport.domain.bus.model

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BusRepository : CoroutineCrudRepository<BusEntity, Long> {
    fun findAllByIdIn(ids: List<Long>): Flow<BusEntity>
}

interface BusStopArrivalRepository : CoroutineCrudRepository<BusStopArrivalEntity, Long> {
    fun findAllByBusIdIn(busIds: List<Long>): Flow<BusStopArrivalEntity>
}
