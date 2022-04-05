package com.memo.api.domain.exception

class PostNotFoundException(clientId: String) : RuntimeException("'$clientId' velog가 존재하지 않습니다.")
