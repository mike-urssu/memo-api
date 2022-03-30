package com.memo.api.domain.service

import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.entity.PostTag
import com.memo.api.domain.model.entity.Tag
import com.memo.api.domain.model.repository.PostTagRepository
import com.memo.api.domain.model.repository.TagRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class TagService(
    private val tagRepository: TagRepository,

    private val postTagRepository: PostTagRepository
) {
    fun createTags(post: Post, tagsFromRequest: List<String>) {
        tagsFromRequest.forEach { name ->
            val tag = tagRepository.save(Tag(name = name))
            val postTag = postTagRepository.save(PostTag(post = post, tag = tag))
            post.postTags.add(postTag)
            tag.postTags.add(postTag)
        }
    }

    fun updateTags(post: Post, tagsFromRequest: List<String>?) {
        if (tagsFromRequest == null)
            return

        tagRepository.deleteAllInBatch(post.tags)
        createTags(post, tagsFromRequest)
    }
}
