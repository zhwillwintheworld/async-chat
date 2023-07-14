package com.xxy.chat.websocket.entity

import com.alibaba.fastjson2.JSONObject
import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.common.message.MessageType
import com.xxy.chat.common.message.UserInfoDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(value = "MessageRecord")
data class MessageRecordEntity(
    @Id
    // 内部mongodb的主键
    var messageId: String?,
    // 前端传来的消息签名
    var messageSignature: String,
    val type: MessageType,
    val data: String,
    val sender: UserInfoDTO,
    val receiver: UserInfoDTO?,
    val extra: JSONObject?,
    val createTime: LocalDateTime,
    val isSelf: Boolean,
) {
    fun transfer():MessageInfo{
        return MessageInfo(
            this.messageId,
            this.messageSignature,
            this.type,
            this.data,
            this.sender,
            this.receiver,
            this.extra,
            this.createTime,
            this.isSelf
        )
    }
    companion object {
        fun transfer(message: MessageInfo): MessageRecordEntity {
            return MessageRecordEntity(
                message.messageId,
                message.messageSignature,
                message.type,
                message.data,
                message.sender,
                message.receiver,
                message.extra,
                message.createTime,
                message.isSelf
            )
        }
    }
}
