package com.memo.api.domain.model.entity

import javax.persistence.*

@Entity
@Table
class PostTag(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:ManyToOne
    @field:JoinColumn(name = "post_id", referencedColumnName = "id")
    val post: Post,

    @field:ManyToOne
    @field:JoinColumn(name = "tag_id", referencedColumnName = "id")
    val tag: Tag
)