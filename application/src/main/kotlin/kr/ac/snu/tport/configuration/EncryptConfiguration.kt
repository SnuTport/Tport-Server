package kr.ac.snu.tport.configuration

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import jakarta.annotation.PostConstruct
import kr.ac.snu.tport.infra.crypto.EncryptUtils
import org.jasypt.encryption.StringEncryptor
import org.springframework.context.annotation.Configuration

@Configuration
@EnableEncryptableProperties
class EncryptConfiguration(
    private val jasyptEncryptor: StringEncryptor
) {
    @PostConstruct
    fun setEncryptor() {
        EncryptUtils.setEncryptor(jasyptEncryptor)
    }
}
