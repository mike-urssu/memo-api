package com.memo.api.domain.service

import com.memo.api.application.request.CreateMemoRequest
import com.memo.api.domain.model.dto.GetMemosDto
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.repository.ImageRepository
import com.memo.api.domain.model.repository.MemoRepository
import com.memo.api.domain.model.repository.TagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemoService(
    private val tagService: TagService,
    private val imageService: ImageService,

    private val memoRepository: MemoRepository,
    private val tagRepository: TagRepository,
    private val imageRepository: ImageRepository
) {
    @Transactional
    fun createMemo(createMemoRequest: CreateMemoRequest) {
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
            val fileSize = imageRepository.countByMemo(memo)
            GetMemosDto(memo, tagSize, fileSize)
        }
    }
}
