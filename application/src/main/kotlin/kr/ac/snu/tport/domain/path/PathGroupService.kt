package kr.ac.snu.tport.domain.path

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.path.model.PathGroupEntity
import kr.ac.snu.tport.domain.path.model.PathGroupRepository
import kr.ac.snu.tport.domain.path.model.SubPathsEntity
import kr.ac.snu.tport.domain.path.model.SubPathsRepository
import org.springframework.stereotype.Service

@Service
class PathGroupService(
    private val pathGroupRepository: PathGroupRepository,
    private val subPathsRepository: SubPathsRepository,
) {
    fun saveAll(pathGroups: List<PathGroup>) = runBlocking { doSaveAll(pathGroups) }

    data class PathGroupKey(val from: String, val to: String)

    private suspend fun doSaveAll(pathGroups: List<PathGroup>) {
        val pathGroupEntities =
            pathGroupRepository.saveAll(pathGroups.map { it.toEntity() }).toList()
                .associateBy { PathGroupKey(it.getOnBusStop, it.getOffBusStop) }

        val subPathsEntities = pathGroups
            .flatMap { it.subPaths(pathGroupEntities[PathGroupKey(it.getOnBusStop, it.getOffBusStop)]!!.id!!) }

        subPathsRepository.saveAll(subPathsEntities)
    }

    private fun PathGroup.toEntity(): PathGroupEntity {
        return PathGroupEntity(
            id = id,
            getOnBusStop = getOnBusStop,
            getOffBusStop = getOffBusStop,
            fare = fare,
            travelTime = travelTime
        )
    }

    private fun PathGroup.subPaths(id: Long): Set<SubPathsEntity> {
        return subPaths.map {
            SubPathsEntity(
                getOnBusStop = it.getOnBusStop,
                getOffBusStop = it.getOffBusStop,
                busId = (it.vehicle as? Bus)?.busId,
                pathGroupId = id,
                type = it.vehicle.typeName()
            )
        }.toSet()
    }
}
