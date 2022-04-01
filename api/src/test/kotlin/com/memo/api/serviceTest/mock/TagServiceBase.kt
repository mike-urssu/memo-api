package com.memo.api.serviceTest.mock

import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.entity.Tag
import com.memo.api.domain.model.repository.PostTagRepository
import com.memo.api.domain.model.repository.TagRepository
import com.memo.api.domain.service.TagService
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
open class TagServiceBase {
    @InjectMocks
    lateinit var tagService: TagService

    @Mock
    lateinit var tagRepository: TagRepository

    @Mock
    lateinit var postTagRepository: PostTagRepository

    private val postId: Int = 1
    private val postTitle: String = "test"
    private val postContent: String = "test"

    val namesWithoutDuplication = arrayListOf("tag1", "tag2", "tag3")
    val namesWithDuplication = arrayListOf("tag1", "tag1", "tag1", "tag2", "tag3")

    fun getMockPost() = Post(id = postId, title = postTitle, body = postContent)

    fun getMockTagsWithoutDuplicatedName() = namesWithoutDuplication.mapIndexed { index, name ->
        Tag(
            id = index + 1,
            name = name,
            postTags = mutableListOf()
        )
    }

    fun getMockTagsByDuplicatedNames() = namesWithDuplication.distinct()
        .mapIndexed { index, name ->
            Tag(
                id = index + 1,
                name = name,
                postTags = mutableListOf()
            )
        }
}
