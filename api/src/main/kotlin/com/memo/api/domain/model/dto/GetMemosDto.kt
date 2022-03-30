package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Post

class GetMemosDto(
    post: Post,
    val tagSize: Int,
    val imageSize: Int
) {
    val id = post.id!!
    val title = post.title
    val content = post.body
    val updatedAt = post.updatedAt
}
