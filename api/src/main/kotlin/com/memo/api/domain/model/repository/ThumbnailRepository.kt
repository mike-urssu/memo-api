package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.Thumbnail
import com.memo.api.domain.model.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ThumbnailRepository : JpaRepository<Thumbnail, Int> {
    fun countByMemo(post: Post): Int
}