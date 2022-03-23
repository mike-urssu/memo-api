package com.memo.api.domain.exception

class MemoNotFoundException(memoId: Int) : RuntimeException("${memoId}번 메모가 존재하지 않습니다.")