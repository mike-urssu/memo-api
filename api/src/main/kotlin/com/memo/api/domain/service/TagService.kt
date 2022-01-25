package com.memo.api.domain.service

import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.entity.Tag
import com.memo.api.domain.model.repository.TagRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TagService(
    private val tagRepository: TagRepository
) {
    @Transactional
    fun createTags(memo: Memo, tagsFromRequest: List<String>) {
        val tags = mutableListOf<Tag>()
        for (tagInRequest in tagsFromRequest) {
            val tag = Tag(
                id = null,
                memo = memo,
                content = tagInRequest
            )
            tags.add(tag)
            memo.tags.add(tag)
        }
        tagRepository.saveAll(tags)
    }
}
