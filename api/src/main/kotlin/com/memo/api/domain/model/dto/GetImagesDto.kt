package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Thumbnail

class GetImagesDto(thumbnail: Thumbnail) {
    val id = thumbnail.id!!
    val fileName = thumbnail.filename
}