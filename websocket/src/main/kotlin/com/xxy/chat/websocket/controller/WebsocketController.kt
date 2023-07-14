package com.xxy.chat.websocket.controller;

import com.xxy.chat.common.bean.UserLoginInfo
import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.websocket.service.MessageService
import kotlinx.coroutines.flow.Flow
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
@MessageMapping("api.im.ws.v1")
class WebsocketController (
    private val messageService: MessageService
){
    @MessageMapping("stream")
    suspend fun getMessage( user:UserLoginInfo): Flow<MessageInfo>{
        return messageService.stream(userId = user.userId)
    }

    @MessageMapping("post")
    suspend fun post( user:UserLoginInfo,@Payload messageInfo:Flow<MessageInfo>){
        return messageService.sendMessage(sender = user.userId, message = messageInfo)
    }
}
