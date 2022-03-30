package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.PostTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostTagRepository : JpaRepository<PostTag, Int>