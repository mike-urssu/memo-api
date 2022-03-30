package com.memo.api.domain.model.entity

import javax.persistence.*

@Entity
@Table
class Thumbnail(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, length = 256)
    val filename: String,

    @field:Column(nullable = false, length = 256)
    val savedName: String
)
