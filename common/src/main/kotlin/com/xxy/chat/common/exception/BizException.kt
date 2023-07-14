package com.xxy.chat.common.exception


class BizException : RuntimeException {
    var code: Int
    override var message: String

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    constructor(error: ErrorConstant) {
        this.code = error.code
        this.message = error.message
    }

}