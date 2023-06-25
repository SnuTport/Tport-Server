package kr.ac.snu.tport.domain.user.model

import kr.ac.snu.tport.infra.crypto.EncryptedString
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users")
data class UserEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("name")
    val name: String,
    @Column("password")
    val password: EncryptedString
) {
    @Column("reg_ts")
    val regTs: LocalDateTime = LocalDateTime.now()

    @Column("upd_ts")
    val updTs: LocalDateTime = LocalDateTime.now()
}
