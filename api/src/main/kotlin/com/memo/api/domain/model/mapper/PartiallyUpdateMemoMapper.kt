package com.memo.api.domain.model.mapper

import com.memo.api.application.request.PartialUpdatePostRequest
import com.memo.api.domain.model.entity.Post
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface PartiallyUpdateMemoMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updatePost(partialUpdatePostRequest: PartialUpdatePostRequest, @MappingTarget post: Post)
}
