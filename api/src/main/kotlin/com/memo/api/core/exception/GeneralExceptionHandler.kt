package com.memo.api.core.exception

import com.memo.api.core.response.ErrorResponse
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
class GeneralExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValid(exception: MethodArgumentNotValidException): ErrorResponse {
        val builder = StringBuilder()
        for (fieldError in exception.bindingResult.fieldErrors) {
            builder.append("[${fieldError.field}](은)는 ${fieldError.defaultMessage} 입력된 값: ${fieldError.rejectedValue}")
        }
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-003", builder.toString())
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestValid(exception: ConstraintViolationException): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-004", "잘못된 데이터 요청입니다.")
    }
}
