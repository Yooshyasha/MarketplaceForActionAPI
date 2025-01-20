package com.yooshyasha.marketplaceforaction.aspect

import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect(
    private val logger: Logger = LoggerFactory.getLogger(LoggingAspect::class.java)
) {
    @AfterThrowing(pointcut = "execution(* com.yooshyasha.marketplaceforaction.services.*.*(..))")
    fun logAfterThrowing(exception: Exception) {
        logger.error("ERROR in Service: ${exception.message}\n${exception.stackTrace}")
    }
}