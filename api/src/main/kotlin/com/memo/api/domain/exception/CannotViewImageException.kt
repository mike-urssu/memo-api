package com.memo.api.domain.exception

class CannotViewImageException(fileName: String) : RuntimeException("${fileName}을(를) 조회하는데 문제가 발생했습니다.")
