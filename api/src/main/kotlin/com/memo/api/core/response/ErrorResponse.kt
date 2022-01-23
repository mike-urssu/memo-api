package com.memo.api.core.response

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse(
    private val timeStamp: LocalDateTime,
    private val status: Int,
    private val error: String,
    private val message: String
) {
    constructor(httpStatus: HttpStatus, errorCode: String, message: String) : this(
        timeStamp = LocalDateTime.now(),
        status = httpStatus.value(),
        error = errorCode,
        message = message
    )
}