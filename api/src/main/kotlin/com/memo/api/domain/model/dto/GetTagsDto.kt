package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Tag

class GetTagsDto(tag: Tag) {
    val content = tag.content
}