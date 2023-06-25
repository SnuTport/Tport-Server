package kr.ac.snu.tport.controller.dto

data class LogInRequest(val name: String, val password: String)
data class TokenResponse(val bearerToken: String)
