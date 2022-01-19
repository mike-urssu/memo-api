package com.memo.api.application.request

import org.springframework.web.multipart.MultipartFile

data class CreateMemoRequest(
    val title: String,
    val content: String,
    val tags: List<String>,
    val files: List<MultipartFile>?
)
