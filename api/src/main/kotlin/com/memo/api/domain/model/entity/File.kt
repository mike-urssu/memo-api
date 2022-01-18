package com.memo.api.domain.model.entity

import javax.persistence.*

@Entity
@Table
class File(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val memo: Memo,

    @Column(nullable = false, length = 256)
    val fileName: String,

    @Column(nullable = false, length = 256)
    val displayedName: String
)
