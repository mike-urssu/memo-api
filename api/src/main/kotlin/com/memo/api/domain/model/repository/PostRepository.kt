package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional

@Repository
interface PostRepository : JpaRepository<Post, Int> {
    fun findAllByIsDeletedIsFalse(pageable: Pageable): Page<Post>

    fun findAllByIsDeletedIsFalseAndTitleContainingOrBodyContaining(
        title: String,
        body: String,
        pageable: Pageable
    ): Page<Post>

    fun findByIdAndIsDeletedIsFalse(memoId: Int): Optional<Post>

    fun findByDeletedAtBefore(time: LocalDateTime): List<Post>
}
