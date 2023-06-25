package kr.ac.snu.tport.domain.path.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PathGroupRepository : CoroutineCrudRepository<PathGroupEntity, Long>

interface SubPathsRepository : CoroutineCrudRepository<SubPathsEntity, Long>
