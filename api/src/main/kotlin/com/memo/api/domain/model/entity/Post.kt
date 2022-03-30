package com.memo.api.domain.model.entity

import com.memo.api.application.request.CreateOrUpdatePostRequest
import com.memo.api.domain.model.dto.GetPostDto
import org.hibernate.Hibernate
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.stream.Collectors
import javax.persistence.*

@Entity
@Table
@DynamicUpdate
data class Post(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, columnDefinition = "VARCHAR(100)")
    var title: String,

    @field:Column(nullable = false, columnDefinition = "TEXT")
    var body: String,

    @OneToOne
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "id")
    var thumbnail: Thumbnail? = null,

    @field:Column(nullable = false)
    var isDeleted: Boolean = false,

    @field:Column(nullable = false, columnDefinition = "DATETIME")
    val releasedAt: LocalDateTime = LocalDateTime.now(),

    @field:Column(nullable = false, columnDefinition = "DATETIME")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = releasedAt,

    @field:Column(columnDefinition = "DATETIME")
    var deletedAt: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "post")
    var postTags: MutableList<PostTag> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Post

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , body = $body , isDeleted = $isDeleted , releasedAt = $releasedAt , updatedAt = $updatedAt , deletedAt = $deletedAt )"
    }

    fun update(updatePostRequest: CreateOrUpdatePostRequest) {
        this.title = updatePostRequest.title
        this.body = updatePostRequest.body
    }

    fun delete() {
        this.isDeleted = true
        this.deletedAt = LocalDateTime.now()
    }

    fun toGetPostDto() = GetPostDto(this)
}
