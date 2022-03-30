package com.memo.api.application.controller

import com.memo.api.application.request.CreateOrUpdatePostRequest
import com.memo.api.application.request.PartialUpdatePostRequest
import com.memo.api.application.response.GetPostResponse
import com.memo.api.application.response.GetPostsResponse
import com.memo.api.domain.service.PostService
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/velog")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(
        @ModelAttribute @Valid createPostRequest: CreateOrUpdatePostRequest
    ) = postService.createPost(createPostRequest)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getPosts(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) keyword: String?
    ): GetPostsResponse {
        val pageable = PageRequest.of(page, size)
        val posts = postService.getPosts(pageable, keyword)
        return GetPostsResponse(posts)
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPost(
        @PathVariable postId: Int
    ) = GetPostResponse(postService.getPost(postId))

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun updatePost(
        @PathVariable postId: Int,
        @Valid @ModelAttribute updatePostRequest: CreateOrUpdatePostRequest
    ) = postService.updatePost(postId, updatePostRequest)

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun partialUpdatePost(
        @PathVariable postId: Int,
        @Valid @ModelAttribute partialUpdatePostRequest: PartialUpdatePostRequest
    ) = postService.partialUpdatePost(postId, partialUpdatePostRequest)

    @DeleteMapping("/{memoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMemo(
        @PathVariable memoId: Int
    ) = postService.deleteMemo(memoId)

    @Scheduled(cron = "*/4 * * * * *")
    fun batchDeleteMemos() = postService.batchDeleteMemos()
}
