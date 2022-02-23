package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Int> {
    fun countByMemo(memo: Memo): Int
}