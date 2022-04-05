package com.memo.api.domain.model.entity

import com.memo.api.domain.util.RNG
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table
class Tag(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, columnDefinition = "CHAR(32)")
    val clientId: String = RNG.generateKey(),

    @field:Column(nullable = false, columnDefinition = "VARCHAR(45)")
    val name: String,

    @field:OneToMany(mappedBy = "tag")
    var postTags: MutableList<PostTag> = mutableListOf()
)
