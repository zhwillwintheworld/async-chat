package com.xxy.chat.common.bean

import com.xxy.chat.common.exception.BizException
import com.xxy.chat.common.exception.ErrorConstant


data class Result<T>(
    val code: Int,
    val msg: String,
    var data: T?
) {
    companion object {
        fun <T> ok(): Result<T> {
            return Result(200, "success", null)
        }

        fun <T> ok(data: T): Result<T> {
            return Result(200, "success", data)
        }

        fun error(code: Int, msg: String): Result<Void> {
            return Result(code, msg, null)
        }

        fun error(error: ErrorConstant): Result<Void> {
            return Result(error.code, error.message, null)
        }

        fun error(biz: BizException): Result<Void> {
            return Result(biz.code, biz.message, null)
        }
    }
}