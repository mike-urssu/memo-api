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
        tagService.createTags(post, namesWithoutDuplication)

        val tags = getMockTagsWithoutDuplicatedName()
        verify(tagRepository, times(1)).saveAll(refEq(tags))
    }


    /*
        TODO 테스트 코드 수정 필요
        getMockTagsWithDuplicatedNames()는 ["defaultTag1", "tag1", "tag2", "tag3"]를 나타내지만
        createTags()는 이미 "defaultTag1"을 갖고 있기 때문에 ["tag1", "tag2", "tag3"]만 save하기를 기대한다.

        expected: ["tag1", "tag2", "tag3"]만 저장
        actual: ["defaultTag1", "tag1", "tag2", "tag3"]을 저장
     */
    @Test
    @DisplayName("태그 생성하기 - 성공(중복이 존재하는 태그)")
    fun createTagsWithDuplicatedNames() {
        tagService.createTags(post, namesWithDuplication)

        val tags = getMockTagsWithDuplicatedNames()
        verify(tagRepository, times(1)).saveAll(refEq(tags))
    }
}
