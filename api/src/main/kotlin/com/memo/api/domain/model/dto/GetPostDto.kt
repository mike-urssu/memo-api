package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Post
import java.util.stream.Collectors

class GetPostDto(
    post: Post
) {
    val postId = post.clientId
    val title = post.title
    val body = post.body
    val tags = post.postTags.stream()
        .map { it.tag.name }
        .collect(Collectors.toList())
    val updatedAt = post.updatedAt
}
