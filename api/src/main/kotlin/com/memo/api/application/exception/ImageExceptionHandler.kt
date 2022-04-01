package com.memo.api.application.exception

import com.memo.api.core.response.ErrorResponse
import com.memo.api.domain.exception.CannotViewImageException
import com.memo.api.domain.exception.ImageNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ImageExceptionHandler {
    @ExceptionHandler(ImageNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleImageNotFound(exception: ImageNotFoundException) =
        ErrorResponse(HttpStatus.NOT_FOUND, "Image-001", exception.message!!)

    @ExceptionHandler(CannotViewImageException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleImageNotFound(exception: CannotViewImageException) =
        ErrorResponse(HttpStatus.CONFLICT, "Image-002", exception.message!!)
}
