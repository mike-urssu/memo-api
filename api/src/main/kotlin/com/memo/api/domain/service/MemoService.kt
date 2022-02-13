package com.memo.api.domain.service

import com.memo.api.application.request.CreateMemoRequest
import com.memo.api.domain.model.dto.GetMemosDto
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.repository.MemoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class MemoService(
    private val tagService: TagService,
    private val fileService: FileService,
    private val memoRepository: MemoRepository
) {
    @Transactional
    fun createMemo(createMemoRequest: CreateMemoRequest) {
        val memo = memoRepository.save(
            Memo(
                id = null,
                title = createMemoRequest.title,
                content = createMemoRequest.content
            )
        )
        tagService.createTags(memo, createMemoRequest.tags)
        fileService.createFiles(memo, createMemoRequest.files)
    }

    @Transactional(readOnly = true)
    fun getMemos(): List<GetMemosDto> = memoRepository.findAllByIsDeletedIsFalse()
        .stream().map { memo ->
            GetMemosDto(memo)
        }.collect(Collectors.toList())
}
