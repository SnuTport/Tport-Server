package kr.ac.snu.tport.controller

import com.fasterxml.jackson.databind.ObjectMapper
import kr.ac.snu.tport.controller.dto.LogInRequest
import kr.ac.snu.tport.controller.dto.TokenResponse
import kr.ac.snu.tport.domain.user.UserService
import kr.ac.snu.tport.infra.crypto.EncryptUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val objectMapper: ObjectMapper,
    private val userService: UserService
) {
    @PostMapping("/login", "/signup")
    suspend fun loginOrSignUp(
        @RequestBody request: LogInRequest
    ): TokenResponse {
        val user = userService.getOrCreate(request.name, request.password)
        return TokenResponse(EncryptUtils.encrypt(objectMapper.writeValueAsString(user)))
    }
}
