package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.entity.PostTag
import com.memo.api.domain.model.entity.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostTagRepository : JpaRepository<PostTag, Int> {
    fun existsByPostAndTag(post: Post, tag: Tag): Boolean
    fun findAllByTag_NameContaining(name: String, pageable: Pageable): Page<PostTag>
}
