package com.memo.api.domain.service

import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.entity.PostTag
import com.memo.api.domain.model.entity.Tag
import com.memo.api.domain.model.repository.PostTagRepository
import com.memo.api.domain.model.repository.TagRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
@Transactional
class TagService(
    private val tagRepository: TagRepository,
    private val postTagRepository: PostTagRepository
) {
    fun createTags(post: Post, names: List<String>) {
        val tagsToSave = names.stream()
            .distinct()
            .filter { name -> !tagRepository.existsByName(name) }
            .map { name -> Tag(name = name) }
            .collect(Collectors.toList())
        tagRepository.saveAll(tagsToSave)
    }

    fun createPostTags(post: Post, names: List<String>) {
        tagRepository.findAllByNameIn(names).stream()
            .filter { tag -> !postTagRepository.existsByPostAndTag(post, tag) }
            .forEach { tag ->
                val postTag = postTagRepository.save(PostTag(post = post, tag = tag))
                tag.postTags.add(postTag)
                post.postTags.add(postTag)
            }
    }

    fun updateTagsIfPresent(post: Post, names: List<String>?) {
        if (names == null)
            return
        deleteUnusedPostTags(post.postTags, names)
        createTags(post, names)
        createPostTags(post, names)
    }

    private fun deleteUnusedPostTags(postTags: List<PostTag>, names: List<String>) {
        val set = names.toSet()
        postTags.stream()
            .filter { postTag -> !set.contains(postTag.tag.name) }
            .forEach { postTag -> postTagRepository.delete(postTag) }
    }
}
