package kr.ac.snu.tport.domain.path.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PathRepository : CoroutineCrudRepository<PathEntity, Long> {
    suspend fun findAllByGetOnBusStopAndGetOffBusStop(
        getOnBusStop: String,
        getOffBusStop: String
    ): List<PathEntity>
}
