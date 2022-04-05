package com.memo.api.application.controller

import com.memo.api.domain.service.ThumbnailService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/velog/images")
class ThumbnailController(
    private val thumbnailService: ThumbnailService
) {
    @ApiOperation("썸네일 조회하기")
    @GetMapping("/{imageId}", produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun viewImage(
        @PathVariable imageId: String
    ) = thumbnailService.viewImage(imageId)
}
