package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Memo

class GetMemosDto(
    memo: Memo,
    val tagSize: Int,
    val fileSize: Int
) {
    val id = memo.id!!
    val title = memo.title
    val content = memo.content
    val updatedAt = memo.updatedAt
}
