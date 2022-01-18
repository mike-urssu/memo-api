package com.memo.api.domain.model.entity

import javax.persistence.*

@Entity
@Table
class Tag(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(nullable = false)
    val memo: Memo,

    @field:Column(nullable = false, columnDefinition = "VARCHAR(45)")
    val content: String
)
