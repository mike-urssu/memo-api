package com.memo.api.domain.service

import com.memo.api.application.request.CreateOrUpdatePostRequest
import com.memo.api.application.request.PartialUpdateMemoRequest
import com.memo.api.domain.exception.MemoNotFoundException
import com.memo.api.domain.model.dto.GetImagesDto
import com.memo.api.domain.model.dto.GetMemoDto
import com.memo.api.domain.model.dto.GetMemosDto
import com.memo.api.domain.model.dto.GetTagsDto
import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.mapper.PartiallyUpdateMemoMapper
import com.memo.api.domain.model.repository.ThumbnailRepository
import com.memo.api.domain.model.repository.PostRepository
import com.memo.api.domain.model.repository.TagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.stream.Collectors

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
        thumbnailService.createThumbnail(post, createPostRequest.thumbnail)
    }

    @Transactional(readOnly = true)
    fun getMemos(pageable: PageRequest, keyword: String?): Page<GetMemosDto> {
        return (
                if (keyword == null)
                    postRepository.findAllByIsDeletedIsFalse(pageable)
                else
                    postRepository.findAllByIsDeletedIsFalseAndTitleContainingOrBodyContaining(
                        keyword,
                        keyword,
                        pageable
                    )
                ).map { GetMemosDto(it) }
    }

    @Transactional(readOnly = true)
    fun getMemo(memoId: Int): GetMemoDto {
        val memo = postRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }

        val tags = memo.tags.stream()
            .map {
                GetTagsDto(it)
            }.collect(Collectors.toList())

        val images = memo.images.stream()
            .map {
                GetImagesDto(it)
            }.collect(Collectors.toList())

        return GetMemoDto(memo, tags, images)
    }

    fun updateMemo(memoId: Int, updateMemoRequest: CreateOrUpdatePostRequest) {
        val memo = postRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }
        memo.update(updateMemoRequest)
        tagService.updateTags(memo, updateMemoRequest.tags)
        thumbnailService.updateImages(memo, updateMemoRequest.images)
    }

    fun updateMemoPartially(memoId: Int, partialUpdateMemoRequest: PartialUpdateMemoRequest) {
        val memo = postRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }
        updateMemoMapper.updateMemo(partialUpdateMemoRequest, memo)
        tagService.updateTags(memo, partialUpdateMemoRequest.tags)
        thumbnailService.updateImages(memo, partialUpdateMemoRequest.images)
    }

    fun deleteMemo(memoId: Int) {
        val memo = postRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }
        memo.delete()
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
