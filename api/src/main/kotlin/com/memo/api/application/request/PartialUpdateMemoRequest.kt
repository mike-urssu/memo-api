package com.memo.api.application.request

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Size

data class PartialUpdateMemoRequest(
    @field:Size(max = 45, message = "Title must not exceed 45 characters.")
    val title: String?,

    val content: String?,

    @field:Size(max = 3, message = "Up to 3 tags can be registered.")
    val tags: List<String>?,

    @field:Size(max = 3, message = "Up to 3 images can be registered.")
    val images: List<MultipartFile>?
)
