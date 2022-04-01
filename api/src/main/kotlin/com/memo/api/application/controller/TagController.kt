package com.memo.api.application.controller

import com.memo.api.application.response.GetTagsResponse
import com.memo.api.domain.service.TagService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/velog/tags")
class TagController(
    private val tagService: TagService
) {
    @ApiOperation("태그 목록 조회하기")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getTags() = GetTagsResponse(tagService.getTags())
}