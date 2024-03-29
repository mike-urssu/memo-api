package com.memo.api.application.request

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class CreateOrUpdatePostRequest(
    @field:NotEmpty(message = "Title must not be empty.")
    @field:Size(max = 100, message = "Title must not exceed 100 characters.")
    val title: String,

    @field:NotEmpty(message = "Content must not be empty.")
    val body: String,

    @NotNull
    val tags: List<String>,

    val thumbnail: MultipartFile?
)
