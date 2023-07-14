package com.xxy.chat.websocket.service

import com.xxy.chat.common.message.MessageInfo
import kotlinx.coroutines.flow.Flow

interface MessageService {
    fun stream(userId:Long): Flow<MessageInfo>
    suspend fun sendMessage(sender:Long,message:Flow<MessageInfo>)
}