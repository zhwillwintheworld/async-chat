package com.xxy.chat.common.message

import com.alibaba.fastjson2.JSONObject
import java.time.LocalDateTime

data class MessageInfo(
    // 内部mongodb的主键
    var messageId:String?,
    // 前端传来的消息签名
    var messageSignature:String,
    val type:MessageType,
    val data:String,
    val sender: UserInfoDTO,
    val receiver:UserInfoDTO?,
    val extra:JSONObject?,
    val createTime:LocalDateTime,
    val isSelf:Boolean,
)

data class UserInfoDTO(
    val userId:Long,
    val username:String,
    val avatar:String,
    val isGroup:Boolean
)