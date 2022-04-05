package com.memo.api.domain.exception

import org.springframework.util.InvalidMimeTypeException

class InvalidFileTypeException(mimeType: String, filename: String) :
    InvalidMimeTypeException(mimeType, "filename: $filename")
