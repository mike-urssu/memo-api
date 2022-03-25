package com.memo.api.domain.service

import com.memo.api.application.request.CreateOrUpdateMemoRequest
import com.memo.api.application.request.PartialUpdateMemoRequest
import com.memo.api.domain.exception.MemoNotFoundException
import com.memo.api.domain.model.dto.GetImagesDto
import com.memo.api.domain.model.dto.GetMemoDto
import com.memo.api.domain.model.dto.GetMemosDto
import com.memo.api.domain.model.dto.GetTagsDto
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.mapper.PartiallyUpdateMemoMapper
import com.memo.api.domain.model.repository.ImageRepository
import com.memo.api.domain.model.repository.MemoRepository
import com.memo.api.domain.model.repository.TagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
@Transactional
class MemoService(
    private val tagService: TagService,
    private val imageService: ImageService,

    private val memoRepository: MemoRepository,
    private val tagRepository: TagRepository,
    private val imageRepository: ImageRepository,

    private val updateMemoMapper: PartiallyUpdateMemoMapper
) {
    fun createMemo(createMemoRequest: CreateOrUpdateMemoRequest) {
        val memo = memoRepository.save(Memo(title = createMemoRequest.title, content = createMemoRequest.content))
        tagService.createTags(memo, createMemoRequest.tags)
        imageService.createImages(memo, createMemoRequest.images)
    }

    @Transactional(readOnly = true)
    fun getMemos(pageable: PageRequest, title: String?, content: String?): Page<GetMemosDto> {
        val memos =
            if (!title.isNullOrEmpty() && !content.isNullOrEmpty())
                memoRepository.findAllByIsDeletedIsFalseAndTitleContainingOrContentContaining(title, content, pageable)
            else if (!title.isNullOrEmpty())
                memoRepository.findAllByIsDeletedIsFalseAndTitleContaining(title, pageable)
            else if (!content.isNullOrEmpty())
                memoRepository.findAllByIsDeletedIsFalseAndContentContaining(content, pageable)
            else
                memoRepository.findAllByIsDeletedIsFalse(pageable)
        return memos.map { memo ->
            val tagSize = tagRepository.countByMemo(memo)
            val imageSize = imageRepository.countByMemo(memo)
            GetMemosDto(memo, tagSize, imageSize)
        }
    }

    @Transactional(readOnly = true)
    fun getMemo(memoId: Int): GetMemoDto {
        val memo = memoRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }

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

    fun updateMemo(memoId: Int, updateMemoRequest: CreateOrUpdateMemoRequest) {
        val memo = memoRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }
        memo.updateMemo(updateMemoRequest)
        tagService.updateTags(memo, updateMemoRequest.tags)
        imageService.updateImages(memo, updateMemoRequest.images)
    }

    fun updateMemoPartially(memoId: Int, partialUpdateMemoRequest: PartialUpdateMemoRequest) {
        val memo = memoRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }
        updateMemoMapper.updateMemo(partialUpdateMemoRequest, memo)
        tagService.updateTags(memo, partialUpdateMemoRequest.tags)
        imageService.updateImages(memo, partialUpdateMemoRequest.images)
    }

    fun deleteMemo(memoId: Int) {
        val memo = memoRepository.findByIdAndIsDeletedIsFalse(memoId).orElseThrow { MemoNotFoundException(memoId) }
        memo.isDeleted = true
        memo.deletedAt = LocalDateTime.now()
    }
}
