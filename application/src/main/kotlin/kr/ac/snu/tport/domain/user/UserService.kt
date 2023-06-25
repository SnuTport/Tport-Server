package kr.ac.snu.tport.domain.user

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kr.ac.snu.tport.domain.user.model.UserEntity
import kr.ac.snu.tport.domain.user.model.UserRepository
import kr.ac.snu.tport.infra.crypto.EncryptedString
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

    suspend fun getOrCreate(name: String, password: String): User {
        val entity = userRepository.findByName(name) ?: return signUp(name, password)

        check(entity.password.value == password) { "Password is incorrect" }
        return toUser(entity)
    }

    private suspend fun signUp(name: String, password: String): User {
        val entity = userRepository.save(UserEntity(name = name, password = EncryptedString(password)))
        return toUser(entity)
    }

    private fun toUser(entity: UserEntity): User {
        return User(
            id = entity.id!!,
            name = entity.name
        )
    }
}
