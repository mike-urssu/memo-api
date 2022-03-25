package com.memo.api.domain.model.entity

import com.memo.api.application.request.CreateOrUpdateMemoRequest
import org.hibernate.Hibernate
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
@DynamicUpdate
data class Memo(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, columnDefinition = "VARCHAR(45)")
    var title: String,

    @field:Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @field:Column(nullable = false)
    var isDeleted: Boolean = false,

    @field:Column(nullable = false, columnDefinition = "DATETIME")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @field:Column(nullable = false, columnDefinition = "DATETIME")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = createdAt,

    @field:Column(columnDefinition = "DATETIME")
    val deletedAt: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "memo")
    var tags: MutableList<Tag> = mutableListOf(),

    @field:OneToMany(mappedBy = "memo")
    var images: MutableList<Image> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Memo

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , content = $content , isDeleted = $isDeleted , createdAt = $createdAt , updatedAt = $updatedAt , deletedAt = $deletedAt )"
    }

    fun updateMemo(createOrUpdateMemoRequest: CreateOrUpdateMemoRequest) {
        this.title = createOrUpdateMemoRequest.title
        this.content = createOrUpdateMemoRequest.content
    }
}
