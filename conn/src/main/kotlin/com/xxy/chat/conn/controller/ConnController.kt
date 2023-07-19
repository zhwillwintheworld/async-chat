package com.xxy.chat.conn.controller

import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.conn.service.MessageService
import kotlinx.coroutines.flow.Flow
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
@MessageMapping("api.im.v1")
class ConnController (
    private val messageService: MessageService
){
    @MessageMapping("stream")
    suspend fun getMessage(): Flow<MessageInfo>{
        return messageService.stream(userId = 1)
    }

    @MessageMapping("post")
    suspend fun post(@Payload messageInfo:MessageInfo){
        println("发送了消息")
        messageService.sendMessage(sender = 1, message = messageInfo)

    }
}
