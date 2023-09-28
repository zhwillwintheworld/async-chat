package com.xxy.chat.web.config

import com.xxy.chat.common.exception.BizException
import com.xxy.chat.common.bean.api.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandle {
    @ExceptionHandler(Exception::class)
    fun globalExceptionHandle(e: Exception): Flow<Result<Void>> {
        e.printStackTrace()
        return flowOf(Result.error(500, "服务异常"))
    }

    @ExceptionHandler(BizException::class)
    fun bizExceptionHandle(biz: BizException): Flow<Result<Void>> {
        return flowOf(Result.error(biz))
    }
}