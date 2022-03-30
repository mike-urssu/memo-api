package com.memo.api.application.exception

import com.memo.api.core.response.ErrorResponse
import com.memo.api.domain.exception.PostNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class MemoExceptionHandler {
    @ExceptionHandler(PostNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleMemoNotFound(exception: PostNotFoundException) =
        ErrorResponse(HttpStatus.NOT_FOUND, "Memo-001", exception.message!!)
}