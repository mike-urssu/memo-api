package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Image

class GetImagesDto(image: Image) {
    val id = image.id!!
    val fileName = image.fileName
}