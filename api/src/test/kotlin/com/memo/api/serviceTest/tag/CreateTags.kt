package com.memo.api.serviceTest.tag

import com.memo.api.serviceTest.mock.TagServiceBase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.refEq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class CreateTags : TagServiceBase() {
    @Test
    @DisplayName("태그 생성하기 - 성공(중복 없는 태그)")
    fun createTags() {
        val post = getMockPost()
        val tags = getMockTagsWithoutDuplication()

        tagService.createTags(post, namesWithoutDuplication)
        verify(tagRepository, times(1)).saveAll(refEq(tags))
    }
}