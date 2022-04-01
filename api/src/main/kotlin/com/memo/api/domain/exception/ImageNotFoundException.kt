package com.memo.api.domain.exception

class ImageNotFoundException(id: Int) : RuntimeException("$id 번 이미지가 존재하지 않습니다.")
