package com.memo.api.application.controller

import com.memo.api.domain.service.ThumbnailService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/velog/images")
class ThumbnailController(
    private val thumbnailService: ThumbnailService
) {
    @GetMapping("/{imageId}", produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun viewImage(
        @PathVariable imageId: Int
    ) = thumbnailService.viewImage(imageId)
}