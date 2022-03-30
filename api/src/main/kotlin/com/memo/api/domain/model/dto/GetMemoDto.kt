package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Post

class GetMemoDto(
    post: Post,
    val tags: List<GetTagsDto>,
    val images: List<GetImagesDto>
) {
    val id = post.id!!
    val title = post.title
    val content = post.body
    val updatedAt = post.updatedAt
}