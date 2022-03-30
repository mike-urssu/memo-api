package com.memo.api.domain.service

import com.memo.api.application.request.CreateOrUpdatePostRequest
import com.memo.api.application.request.PartialUpdatePostRequest
import com.memo.api.domain.exception.PostNotFoundException
import com.memo.api.domain.model.dto.GetPostsDto
import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.mapper.PartiallyUpdateMemoMapper
import com.memo.api.domain.model.repository.PostRepository
import com.memo.api.domain.model.repository.TagRepository
import com.memo.api.domain.model.repository.ThumbnailRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class PostService(
    private val tagService: TagService,
    private val thumbnailService: ThumbnailService,

    private val postRepository: PostRepository,
    private val tagRepository: TagRepository,
    private val thumbnailRepository: ThumbnailRepository,

    private val updateMemoMapper: PartiallyUpdateMemoMapper
) {
    fun createPost(createPostRequest: CreateOrUpdatePostRequest) {
        val post = postRepository.save(Post(title = createPostRequest.title, body = createPostRequest.body))
        tagService.createTags(post, createPostRequest.tags)
        thumbnailService.createThumbnailIfPresent(post, createPostRequest.thumbnail)
    }

    @Transactional(readOnly = true)
    fun getPosts(pageable: PageRequest, keyword: String?): Page<GetPostsDto> {
        return (
                if (keyword == null)
                    postRepository.findAllByIsDeletedIsFalse(pageable)
                else
                    postRepository.findAllByIsDeletedIsFalseAndTitleContainingOrBodyContaining(
                        keyword,
                        keyword,
                        pageable
                    )
                ).map { GetPostsDto(it) }
    }

    @Transactional(readOnly = true)
    fun getPost(postId: Int) =
        postRepository.findByIdAndIsDeletedIsFalse(postId).orElseThrow { PostNotFoundException(postId) }.toGetPostDto()

    fun updatePost(postId: Int, updatePostRequest: CreateOrUpdatePostRequest) {
        val post = postRepository.findByIdAndIsDeletedIsFalse(postId).orElseThrow { PostNotFoundException(postId) }
        post.update(updatePostRequest)
        tagService.updateTagsIfPresent(post, updatePostRequest.tags)
        thumbnailService.updateThumbnail(post, updatePostRequest.thumbnail)
    }

    fun partialUpdatePost(postId: Int, partialUpdatePostRequest: PartialUpdatePostRequest) {
        val post = postRepository.findByIdAndIsDeletedIsFalse(postId).orElseThrow { PostNotFoundException(postId) }
        updateMemoMapper.updatePost(partialUpdatePostRequest, post)
        tagService.updateTagsIfPresent(post, partialUpdatePostRequest.tags)
        thumbnailService.updateThumbnail(post, partialUpdatePostRequest.thumbnail)
    }

    fun deletePost(postId: Int) {
        val post = postRepository.findByIdAndIsDeletedIsFalse(postId).orElseThrow { PostNotFoundException(postId) }
        post.delete()
    }

    fun batchDeleteMemos() {
        val deletedBefore = LocalDateTime.now().minusSeconds(10)
        val memos = postRepository.findByDeletedAtBefore(deletedBefore)
        memos.forEach { memo ->
            tagRepository.deleteAllInBatch(memo.tags)
            thumbnailService.deleteImages(memo.images)
        }
        postRepository.deleteAllInBatch(memos)
    }
}
