package com.memo.api.domain.model.dto

import com.memo.api.domain.model.entity.Memo

class GetMemosDto(
    memo: Memo
) {
    val id = memo.id!!
    val title = memo.title
    val updatedAt = memo.updatedAt

    val tagSize = memo.tags.size

    val fileSize = memo.files.size
}
