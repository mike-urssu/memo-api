package com.memo.api.domain.model.entity

import javax.persistence.*

@Entity
@Table
class File(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(nullable = false)
    val memo: Memo,

    @field:Column(nullable = false, length = 256)
    val fileName: String,

    @field:Column(nullable = false, length = 256
    val displayedName: String
)
