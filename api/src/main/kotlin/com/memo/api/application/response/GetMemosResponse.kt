package com.memo.api.application.response

import com.memo.api.domain.model.dto.GetMemosDto
import org.springframework.data.domain.Page

class GetMemosResponse(
    val memos: Page<GetMemosDto>
)
