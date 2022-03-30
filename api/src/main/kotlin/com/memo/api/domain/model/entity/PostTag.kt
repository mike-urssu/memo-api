package com.memo.api.domain.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table
class PostTag(
    @Id
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    val post: Post,

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    val tag: Tag
)