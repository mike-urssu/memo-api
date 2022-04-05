package com.memo.api.domain.model.entity

import com.memo.api.domain.util.RNG
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class Thumbnail(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, columnDefinition = "CHAR(32)")
    val clientId: String = RNG.generateKey(),

    @field:Column(nullable = false, length = 256)
    val filename: String,

    @field:Column(nullable = false, length = 256)
    val savedName: String
)
