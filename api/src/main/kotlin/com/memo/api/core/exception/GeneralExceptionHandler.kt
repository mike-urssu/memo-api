package com.memo.api.core.exception

import com.memo.api.core.response.ErrorResponse
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
class GeneralExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValid(exception: MethodArgumentNotValidException): ErrorResponse {
        val builder = StringBuilder()
        for (fieldError in exception.bindingResult.fieldErrors) {
            println("[${fieldError.field}](은)는 ${fieldError.defaultMessage} 입력된 값: ${fieldError.rejectedValue}")
            builder.append("[${fieldError.field}](은)는 ${fieldError.defaultMessage} 입력된 값: ${fieldError.rejectedValue}")
        }
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-003", builder.toString())
    }
}
