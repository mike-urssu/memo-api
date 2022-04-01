package com.memo.api.application.response

import com.memo.api.domain.model.dto.GetTagsDto

class GetTagsResponse(
    val tags: List<GetTagsDto>
) {
    val postCounts = tags.sumOf { it.postsCount }
}
