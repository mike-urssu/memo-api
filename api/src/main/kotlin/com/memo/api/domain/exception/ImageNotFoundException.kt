package com.memo.api.domain.exception

class ImageNotFoundException(clientId: String) : RuntimeException("'$clientId' 이미지가 존재하지 않습니다.")
