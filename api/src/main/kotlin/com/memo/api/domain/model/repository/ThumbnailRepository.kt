package com.memo.api.domain.model.repository

import com.memo.api.domain.model.entity.Thumbnail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ThumbnailRepository : JpaRepository<Thumbnail, Int> {
    fun findByClientId(clientId: String): Optional<Thumbnail>
}
