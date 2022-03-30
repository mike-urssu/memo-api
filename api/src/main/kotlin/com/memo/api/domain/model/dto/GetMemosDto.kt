package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Post
import java.util.stream.Collectors

class GetMemosDto(
    post: Post
) {
    val id = post.id!!
    val title = post.title
    val body = post.body
    val savedName = post.thumbnail!!.savedName
    val tags = post.postTags.stream().map {
        it.tag.name
    }.collect(Collectors.toList())
    val updatedAt = post.updatedAt
}
