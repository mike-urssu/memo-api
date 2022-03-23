package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Memo

class GetMemoDto(
    memo: Memo,
    val tags: List<GetTagsDto>,
    val images: List<GetImagesDto>
) {
    val id = memo.id!!
    val title = memo.title
    val content = memo.content
    val updatedAt = memo.updatedAt
}