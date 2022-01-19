package com.memo.api.domain.service

import com.memo.api.application.request.CreateMemoRequest
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.repository.MemoRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class MemoService(
    private val tagService: TagService,
    private val fileService: FileService,
    private val memoRepository: MemoRepository
) {
    private val log = KotlinLogging.logger {}

    fun createMemo(createMemoRequest: CreateMemoRequest) {
        val memo = memoRepository.save(
            Memo(
                id = null,
                title = createMemoRequest.title,
                content = createMemoRequest.content
            )
        )
        log.info { "tags: ${memo.tags.size}" }
        log.info { "files: ${memo.files.size}" }

        tagService.createTags(memo, createMemoRequest.tags)
        fileService.createFiles(memo, createMemoRequest.files)

        log.info { "tags: ${memo.tags.size}" }
        log.info { "files: ${memo.files.size}" }
    }
}
