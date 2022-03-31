package com.memo.api.domain.exception

import org.springframework.util.InvalidMimeTypeException

class InvalidFileTypeException(mimeType: String) : InvalidMimeTypeException(mimeType, "해당 파일은 이미지 형식이 아닙니다.")