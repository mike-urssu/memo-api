package com.memo.api.domain.model.mapper

import com.memo.api.application.request.PartialUpdatePostRequest
import com.memo.api.domain.model.entity.Post
import org.mapstruct.*

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface PartiallyUpdateMemoMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updatePost(partialUpdatePostRequest: PartialUpdatePostRequest, @MappingTarget post: Post)
}