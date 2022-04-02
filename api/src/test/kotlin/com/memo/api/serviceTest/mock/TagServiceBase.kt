package com.memo.api.serviceTest.mock

import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.entity.PostTag
import com.memo.api.domain.model.entity.Tag
import com.memo.api.domain.model.repository.PostRepository
import com.memo.api.domain.model.repository.PostTagRepository
import com.memo.api.domain.model.repository.TagRepository
import com.memo.api.domain.service.TagService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
open class TagServiceBase {
    @InjectMocks
    lateinit var tagService: TagService

    @Mock
    lateinit var postRepository: PostRepository

    @Mock
    lateinit var tagRepository: TagRepository

    @Mock
    lateinit var postTagRepository: PostTagRepository

    lateinit var post: Post

    val namesWithoutDuplication = arrayListOf("tag1", "tag2", "tag3")
    fun getMockTagsWithoutDuplicatedName() = namesWithoutDuplication.map { name -> Tag(name = name) }

    val namesWithDuplication = arrayListOf("defaultTag1", "defaultTag1", "tag1", "tag1", "tag2", "tag3")
    fun getMockTagsWithDuplicatedNames() = namesWithDuplication.distinct().map { name -> Tag(name = name) }

    @BeforeEach
    fun init() {
        post = Post(id = 1, title = "test title", body = "test body")
        val tags = arrayListOf(
            Tag(id = 1, name = "defaultTag1"),
            Tag(id = 2, name = "defaultTag2"),
            Tag(id = 3, name = "defaultTag3")
        )
        val postTags = arrayListOf(
            PostTag(id = 1, post = post, tag = tags[0]),
            PostTag(id = 2, post = post, tag = tags[1]),
            PostTag(id = 3, post = post, tag = tags[2])
        )

        postRepository.save(post)
        tags.forEach { tag -> tagRepository.save(tag) }
        postTags.forEachIndexed { index, postTag ->
            postTagRepository.save(postTag)
            post.postTags.add(postTag)
            tags[index].postTags.add(postTag)
        }
    }
}
