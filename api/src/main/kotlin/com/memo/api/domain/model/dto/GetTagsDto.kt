package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Tag

class GetTagsDto(
    tag: Tag
) {
    val tagId = tag.id!!
    val name = tag.name
    val postsCount = tag.postTags.size
}
