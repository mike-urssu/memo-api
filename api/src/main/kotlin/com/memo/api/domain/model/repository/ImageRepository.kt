package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.Image
import com.memo.api.domain.model.entity.Memo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<Image, Int> {
    fun countByMemo(memo: Memo): Int
}