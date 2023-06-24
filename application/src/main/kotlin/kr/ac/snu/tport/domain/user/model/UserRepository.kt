package kr.ac.snu.tport.domain.user.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<UserEntity, Long> {
    suspend fun findByName(name: String): UserEntity?
}
