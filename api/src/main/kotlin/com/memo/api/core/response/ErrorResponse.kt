package com.memo.api.core.response

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse(
    val timeStamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String
) {
    constructor(httpStatus: HttpStatus, errorCode: String, message: String) : this(
        timeStamp = LocalDateTime.now(),
        status = httpStatus.value(),
        error = errorCode,
        message = message
    )
}
