package com.memo.api.domain.service

import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.entity.Tag
import com.memo.api.domain.model.repository.TagRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
class TagService(
    private val tagRepository: TagRepository
) {
    @Transactional
    fun createTags(memo: Memo, tagsFromRequest: List<String>) {
        val tags = tagsFromRequest.stream()
            .map { content ->
                val tag = Tag(memo = memo, content = content)
                memo.tags.add(tag)
                tag
            }.collect(Collectors.toList())
        tagRepository.saveAll(tags)
    }

    @Transactional
    fun updateTags(memo: Memo, tagsFromRequest: List<String>?) {
        if (tagsFromRequest == null)
            return

        tagRepository.deleteAllInBatch(memo.tags)
        createTags(memo, tagsFromRequest)
    }
}
