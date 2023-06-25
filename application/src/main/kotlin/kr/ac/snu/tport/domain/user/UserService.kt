package kr.ac.snu.tport.domain.user

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kr.ac.snu.tport.domain.user.model.UserEntity
import kr.ac.snu.tport.domain.user.model.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    suspend fun findAll(ids: List<Long>): List<User> {
        return userRepository.findAllById(ids)
            .toList()
            .map(::toUser)
    }

    private fun toUser(entity: UserEntity): User {
        return User(
            id = entity.id!!,
            name = entity.name
        )
    }
}
