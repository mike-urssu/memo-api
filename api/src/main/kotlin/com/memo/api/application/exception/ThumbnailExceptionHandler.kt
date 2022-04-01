package com.memo.api.application.exception

import com.memo.api.core.response.ErrorResponse
import com.memo.api.domain.exception.InvalidFileTypeException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ThumbnailExceptionHandler {
    @ExceptionHandler(InvalidFileTypeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidFileType(exception: InvalidFileTypeException) =
        ErrorResponse(HttpStatus.BAD_REQUEST, "Thumbnail-001", exception.message!!)
}
