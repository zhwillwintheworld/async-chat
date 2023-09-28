package com.xxy.chat.common.bean.api

data class UserLoginInfo(
    val userId: Long,
    val username: String,
    val status: Int,
    val token: String,
    val avatar: String
)