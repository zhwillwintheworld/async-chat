package com.xxy.chat.conn.service

import com.xxy.chat.common.message.MessageInfo
import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Mono

interface MessageService {
    fun stream(userId:Long): Flow<MessageInfo>
    suspend fun sendMessage(sender:Long,message: Mono<MessageInfo>)
}