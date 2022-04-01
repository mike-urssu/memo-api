package com.memo.api.core.config

import mu.KotlinLogging
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class AOPConfig {
    private val log = KotlinLogging.logger { }

    @Before("execution(* *..controller.*.*(..))")
    fun recordRequestURI() {
        val requestAttributes = RequestContextHolder.getRequestAttributes() ?: return
        val request = (requestAttributes as ServletRequestAttributes).request
        val queryString = request.queryString
        log.info { "[${request.method}] " + request.requestURI + if (queryString == null) "" else "?" + request.queryString }
    }
}