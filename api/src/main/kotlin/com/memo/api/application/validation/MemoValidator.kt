package com.memo.api.application.validation

import com.memo.api.application.request.CreateMemoRequest
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class MemoValidator : Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return CreateMemoRequest::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        val request = target as CreateMemoRequest
        if(ObjectUtils.isEmpty(request.title)){

        }

    }
}