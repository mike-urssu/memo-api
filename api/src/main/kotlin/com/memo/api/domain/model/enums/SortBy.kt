package com.memo.api.domain.model.enums

import org.springframework.data.domain.Sort

enum class SortBy(val sort: Sort) {
    BASIC(Sort.by(Sort.Direction.DESC, "id")),
    TITLE(Sort.by(Sort.Direction.ASC, "title")),
    CONTENT(Sort.by(Sort.Direction.ASC, "content")),
    TAG(Sort.by(Sort.Direction.ASC, "tag"))
}
