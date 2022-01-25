package com.memo.api.domain.service

import com.memo.api.application.request.CreateMemoRequest
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.repository.MemoRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemoService(
    private val tagService: TagService,
    private val fileService: FileService,
    private val memoRepository: MemoRepository
) {
    private val log = KotlinLogging.logger {}

    @Transactional
    fun createMemo(createMemoRequest: CreateMemoRequest) {
        val memo = memoRepository.save(
            Memo(
                id = null,
                title = createMemoRequest.title,
                content = createMemoRequest.content
            )
        )
//        tagService.createTags(memo, createMemoRequest.tags)
//        fileService.createFiles(memo, createMemoRequest.files)
    }
}
