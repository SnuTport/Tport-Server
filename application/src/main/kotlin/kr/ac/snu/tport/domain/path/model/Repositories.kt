package kr.ac.snu.tport.domain.path.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PathGroupRepository : CoroutineCrudRepository<PathGroupEntity, Long> {
    suspend fun findAllByGetOnBusStopAndGetOffBusStop(
        getOnBusStop: String,
        getOffBusStop: String
    ): List<PathGroupEntity>
}

interface SubPathsRepository : CoroutineCrudRepository<SubPathsEntity, Long> {
    suspend fun findAllByPathGroupId(pathGroupId: Long): List<SubPathsEntity>
    suspend fun findAllByPathGroupIdIn(pathGroupIds: List<Long>): List<SubPathsEntity>
}
