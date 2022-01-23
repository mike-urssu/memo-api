package com.memo.api.application.request

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class CreateMemoRequest(
    @NotEmpty(message = "Title is required")
    val title: String,
    @Size(max = 45, message = "Maximum content length is 45")
    val content: String,
    val tags: List<String>,
    val files: List<MultipartFile>?
)
