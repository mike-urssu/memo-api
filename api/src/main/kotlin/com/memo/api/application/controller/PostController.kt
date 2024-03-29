package com.memo.api.application.controller

import com.memo.api.application.request.CreateOrUpdatePostRequest
import com.memo.api.application.request.PartialUpdatePostRequest
import com.memo.api.application.response.GetPostResponse
import com.memo.api.application.response.GetPostsResponse
import com.memo.api.domain.service.PostService
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/velog/posts")
class PostController(
    private val postService: PostService
) {
    @ApiOperation("velog 생성하기")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(
        @ModelAttribute @Valid createPostRequest: CreateOrUpdatePostRequest
    ) = postService.createPost(createPostRequest)

    @ApiOperation("velog 목록 검색 및 조회하기")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getPosts(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) tag: String?
    ): GetPostsResponse {
        val pageable = PageRequest.of(page, size)
        val posts = postService.getPosts(pageable, keyword, tag)
        return GetPostsResponse(posts)
    }

    @ApiOperation("특정 velog 조회하기")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPost(
        @PathVariable postId: String
    ) = GetPostResponse(postService.getPost(postId))

    @ApiOperation("특정 velog 수정하기")
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun updatePost(
        @PathVariable postId: String,
        @Valid @ModelAttribute updatePostRequest: CreateOrUpdatePostRequest
    ) = postService.updatePost(postId, updatePostRequest)

    @ApiOperation("특정 velog 일부 수정하기")
    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun partialUpdatePost(
        @PathVariable postId: String,
        @Valid @ModelAttribute partialUpdatePostRequest: PartialUpdatePostRequest
    ) = postService.partialUpdatePost(postId, partialUpdatePostRequest)

    @ApiOperation("특정 velog 삭제하기")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePost(
        @PathVariable postId: String
    ) = postService.deletePost(postId)

    @ApiOperation("특정 velog 삭제하기")
    @Scheduled(cron = "0 * * * * *")
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun batchDeletePosts() = postService.batchDeletePosts()
}
