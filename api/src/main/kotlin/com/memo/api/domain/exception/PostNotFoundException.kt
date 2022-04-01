package com.memo.api.domain.exception

class PostNotFoundException(postId: Int) : RuntimeException("${postId}번 velog가 존재하지 않습니다.")
