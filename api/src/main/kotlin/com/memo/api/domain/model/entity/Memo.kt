package com.memo.api.domain.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
class Memo(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, columnDefinition = "VARCHAR(45)")
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(nullable = false)
    val isDeleted: Boolean = false,

    @Column(nullable = false, columnDefinition = "DATETIME")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, columnDefinition = "DATETIME")
    val updatedAt: LocalDateTime = createdAt,

    @Column(columnDefinition = "DATETIME")
    val deletedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "memo")
    val tags: List<Tag>,

    @OneToMany(mappedBy = "memo")
    val files: List<File>,
)
