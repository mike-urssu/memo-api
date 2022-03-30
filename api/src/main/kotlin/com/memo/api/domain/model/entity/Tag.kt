package com.memo.api.domain.model.entity

import javax.persistence.*

@Entity
@Table
class Tag(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, columnDefinition = "VARCHAR(45)")
    val name: String,

    @field:OneToMany(mappedBy = "tag")
    var postTags: MutableList<PostTag> = mutableListOf()
)
