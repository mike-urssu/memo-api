package com.memo.api.application.controller

import com.memo.api.application.request.CreateOrUpdatePostRequest
import com.memo.api.application.request.PartialUpdateMemoRequest
import com.memo.api.application.response.GetMemoResponse
import com.memo.api.application.response.GetPostsResponse
import com.memo.api.domain.service.PostService
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/memos")
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

    @GetMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMemo(
        @PathVariable memoId: Int
    ) = GetMemoResponse(postService.getMemo(memoId))

    @PutMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateMemo(
        @PathVariable memoId: Int,
        @Valid @ModelAttribute updateMemoRequest: CreateOrUpdatePostRequest
    ) = postService.updateMemo(memoId, updateMemoRequest)

    @PatchMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun partialUpdateMemo(
        @PathVariable memoId: Int,
        @Valid @ModelAttribute partialUpdateMemoRequest: PartialUpdateMemoRequest
    ) = postService.updateMemoPartially(memoId, partialUpdateMemoRequest)

    @DeleteMapping("/{memoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMemo(
        @PathVariable memoId: Int
    ) = postService.deleteMemo(memoId)

    @Scheduled(cron = "*/4 * * * * *")
    fun batchDeleteMemos() = postService.batchDeleteMemos()
}
