package com.memo.api.application.response

import com.memo.api.domain.model.dto.GetPostsDto
import org.springframework.data.domain.Page

class GetPostsResponse(
    val posts: Page<GetPostsDto>
)
