package com.memo.api.application.controller

import com.memo.api.application.request.CreateOrUpdateMemoRequest
import com.memo.api.application.request.PartialUpdateMemoRequest
import com.memo.api.application.response.GetMemoResponse
import com.memo.api.application.response.GetMemosResponse
import com.memo.api.domain.service.MemoService
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/memos")
class MemoController(
    private val memoService: MemoService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMemo(
        @ModelAttribute @Valid createMemoRequest: CreateOrUpdateMemoRequest
    ) = memoService.createMemo(createMemoRequest)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getMemos(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) content: String?
    ): GetMemosResponse {
        val pageable = PageRequest.of(page, size)
        val memos = memoService.getMemos(pageable, title, content)
        return GetMemosResponse(memos)
    }

    @GetMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMemo(
        @PathVariable memoId: Int
    ) = GetMemoResponse(memoService.getMemo(memoId))

    @PutMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateMemo(
        @PathVariable memoId: Int,
        @Valid @ModelAttribute updateMemoRequest: CreateOrUpdateMemoRequest
    ) = memoService.updateMemo(memoId, updateMemoRequest)

    @PatchMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun partialUpdateMemo(
        @PathVariable memoId: Int,
        @Valid @ModelAttribute partialUpdateMemoRequest: PartialUpdateMemoRequest
    ) = memoService.updateMemoPartially(memoId, partialUpdateMemoRequest)

    @DeleteMapping("/{memoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMemo(
        @PathVariable memoId: Int
    ) = memoService.deleteMemo(memoId)

    @Scheduled(cron = "*/4 * * * * *")
    fun batchDeleteMemos() = memoService.batchDeleteMemos()
}
