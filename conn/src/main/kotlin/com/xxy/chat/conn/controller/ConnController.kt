package com.xxy.chat.conn.controller

import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.conn.service.MessageService
import io.rsocket.resume.RSocketSession
import kotlinx.coroutines.flow.Flow
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.rsocket.annotation.ConnectMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

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
    suspend fun post(@Payload messageInfo: Mono<MessageInfo>,session:RSocketSession){
        messageService.sendMessage(sender = 1, message = messageInfo)
    }

    @MessageMapping("heartbeat")
    suspend fun heartbeat(@Payload messageInfo: Mono<MessageInfo>,session:RSocketSession){
        messageService.sendMessage(sender = 1, message = messageInfo)
    }

    @ConnectMapping
    suspend fun connect(@Payload messageInfo: Mono<MessageInfo>,session:RSocketSession){
        messageService.sendMessage(sender = 1, message = messageInfo)
    }
}
