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

        tagRepository.saveAll(tagsToSave).forEach { tag ->
            val postTag = postTagRepository.save(PostTag(post = post, tag = tag))
            tag.postTags.add(postTag)
            post.postTags.add(postTag)
        }

//        names.stream()
//            .distinct()
//            .forEach { name ->
//                val tag = tagRepository.findByName(name)
//                    .orElseGet { tagRepository.save(Tag(name = name)) }
//                val postTag = postTagRepository.save(PostTag(post = post, tag = tag))
//                post.postTags.add(postTag)
//                tag.postTags.add(postTag)
//            }
    }

    fun updateTagsIfPresent(post: Post, tagsFromRequest: List<String>?) {
        if (tagsFromRequest == null)
            return

        post.postTags.forEach { postTag ->
            postTagRepository.delete(postTag)
            tagRepository.delete(postTag.tag)
        }

        createTags(post, tagsFromRequest)
    }
}
