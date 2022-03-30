package com.memo.api.domain.model.mapper

import com.memo.api.application.request.PartialUpdateMemoRequest
import com.memo.api.domain.model.entity.Post
import org.mapstruct.*

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface PartiallyUpdateMemoMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "images", ignore = true)
    fun updateMemo(partialUpdateMemoRequest: PartialUpdateMemoRequest, @MappingTarget post: Post)
}