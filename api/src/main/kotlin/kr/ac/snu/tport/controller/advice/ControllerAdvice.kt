package kr.ac.snu.tport.controller.advice

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Throwable::class)
    fun handleThrowableException(e: Throwable): ErrorDto {
        return ErrorDto("에러 메시지 : ${e.message}", "$e")
    }

    data class ErrorDto(
        val errorMessage: String,
        val fullErrorMessage: String
    )
}
