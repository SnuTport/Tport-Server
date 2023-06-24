package kr.ac.snu.tport.user

import kotlinx.coroutines.runBlocking
import kr.ac.snu.tport.domain.user.model.UserEntity
import kr.ac.snu.tport.domain.user.model.UserRepository
import kr.ac.snu.tport.infra.crypto.EncryptedString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository
) {

    @Test
    @DisplayName("유저 저장 & 조회")
    fun saveUser(): Unit = runBlocking {
        // when
        val user =
            userRepository.findByName("someUser") ?:
            userRepository.save(UserEntity(name = "someUser", password = EncryptedString("somePassword")))

        // then
        assertThat(user.password.value).isEqualTo("somePassword")
        userRepository.delete(user)
    }
}
