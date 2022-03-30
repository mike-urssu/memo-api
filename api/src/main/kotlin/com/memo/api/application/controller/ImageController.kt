package com.memo.api.application.controller

import com.memo.api.domain.service.ImageService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/images")
class ImageController(
    private val imageService: ImageService
) {
    @GetMapping("/{imageId}", produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun viewImage(
        @PathVariable imageId: Int
    ) = imageService.viewImage(imageId)
}