package com.memo.api.domain.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
class Memo(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, columnDefinition = "VARCHAR(45)")
    val title: String,

    @field:Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @field:Column(nullable = false)
    val isDeleted: Boolean = false,

    @field:Column(nullable = false, columnDefinition = "DATETIME")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @field:Column(nullable = false, columnDefinition = "DATETIME")
    val updatedAt: LocalDateTime = createdAt,

    @field:Column(columnDefinition = "DATETIME")
    val deletedAt: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "memo")
    val tags: MutableList<Tag> = mutableListOf(),

    @field:OneToMany(mappedBy = "memo")
    val files: MutableList<File> = mutableListOf()
)
