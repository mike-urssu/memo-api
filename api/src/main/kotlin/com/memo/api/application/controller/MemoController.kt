package com.memo.api.application.controller

import com.memo.api.application.request.CreateMemoRequest
import com.memo.api.domain.service.MemoService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/memos")
class MemoController(
    private val memoService: MemoService,
) {
    private val log = KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMemo(
        @Valid @ModelAttribute createMemoRequest: CreateMemoRequest,
        bindingResult: BindingResult
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.allErrors.map { objectError ->
                {
                    log.info { "${objectError.defaultMessage}" }
                }
            }

        }

        memoService.createMemo(createMemoRequest)
    }
}
