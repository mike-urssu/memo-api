package com.memo.api.application.request

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Size

data class PartialUpdatePostRequest(
    @field:Size(max = 100, message = "Title must not exceed 45 characters.")
    val title: String?,

    val body: String?,

    val tags: List<String>?,

    val thumbnail: MultipartFile?
)
