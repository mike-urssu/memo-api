package com.memo.api.application.controller

import com.memo.api.application.request.CreateMemoRequest
import com.memo.api.domain.service.MemoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/memos")
class MemoController(
    private val memoService: MemoService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMemo(
        @RequestBody @Valid createMemoRequest: CreateMemoRequest
    ) {
        memoService.createMemo(createMemoRequest)
    }
}
