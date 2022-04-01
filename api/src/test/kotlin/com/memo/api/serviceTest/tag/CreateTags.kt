package com.memo.api.serviceTest.tag

import com.memo.api.serviceTest.mock.TagServiceBase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.refEq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class CreateTags : TagServiceBase() {
    @Test
    @DisplayName("태그 생성하기 - 성공(중복이 존재하지 않는 태그)")
    fun createTagsWithoutDuplicatedNames() {
        val post = getMockPost()
        val tags = getMockTagsWithoutDuplicatedName()

        tagService.createTags(post, namesWithoutDuplication)
        verify(tagRepository, times(1)).saveAll(refEq(tags))
    }

    @Test
    @DisplayName("태그 생성하기 - 성공(중복이 존재하는 태그)")
    fun createTagsWithDuplicatedNames() {
        val post = getMockPost()
        val tags = getMockTagsByDuplicatedNames()

        tagService.createTags(post, namesWithDuplication)
        verify(tagRepository, times(1)).saveAll(refEq(tags))
    }
}
