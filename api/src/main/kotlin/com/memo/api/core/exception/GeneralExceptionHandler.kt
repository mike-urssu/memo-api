package com.memo.api.core.exception

import com.memo.api.core.response.ErrorResponse
import mu.KotlinLogging
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
class GeneralExceptionHandler {
    private val log = KotlinLogging.logger { }

    @ExceptionHandler(BindException::class, MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRequestInvalid(exception: BindException): ErrorResponse {
        val builder = StringBuilder()
        for (error in exception.bindingResult.fieldErrors)
            builder.appendLine("[field] ${error.field} [message] ${error.defaultMessage} [rejectedValue] ${error.rejectedValue}")
        log.info { builder.toString() }
        return ErrorResponse(HttpStatus.BAD_REQUEST, "System-003", builder.toString())
    }
}
