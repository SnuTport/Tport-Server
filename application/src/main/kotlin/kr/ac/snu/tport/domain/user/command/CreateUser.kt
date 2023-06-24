package kr.ac.snu.tport.domain.user.command

data class CreateUser(
    val name: String,
    val password: String
)
