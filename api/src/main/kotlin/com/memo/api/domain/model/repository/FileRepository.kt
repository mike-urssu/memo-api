package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<File, Int>