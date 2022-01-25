package com.memo.api.application.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class CreateMemoRequest(
    @field:NotEmpty(message = "Title is required")
    val title: String,

    @field:Size(max = 45, message = "Maximum content length is 45")
    val content: String,

    @field:NotEmpty
    val tags: List<String>,

//    val files: List<MultipartFile>?
)
