package com.memo.api.domain.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

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
