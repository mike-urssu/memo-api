package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.Memo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemoRepository : JpaRepository<Memo, Int> {
    fun findAllByIsDeletedIsFalse(pageable: Pageable): Page<Memo>

    fun findAllByIsDeletedIsFalseAndTitleContainingOrContentContaining(
        title: String,
        content: String,
        pageable: Pageable
    ): Page<Memo>

    fun findAllByIsDeletedIsFalseAndTitleContaining(title: String, pageable: Pageable): Page<Memo>

    fun findAllByIsDeletedIsFalseAndContentContaining(content: String, pageable: Pageable): Page<Memo>
}
