package com.memo.api.domain.model.entity

import javax.persistence.*

@Entity
@Table
class Tag(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val memo: Memo,

    @Column(nullable = false, columnDefinition = "VARCHAR(45)")
    val content: String
)
