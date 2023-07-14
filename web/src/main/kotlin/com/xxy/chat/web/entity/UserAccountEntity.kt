package com.xxy.chat.web.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(schema = "xxy", name = "user_account")
data class UserAccountEntity(
    @Id
    var userId: Long?,
    var accountName: String?,
    var password: String?,
    var wechatOpenid: String?,
    var aliOpenid: String?,
    val status: Int
)