package com.xxy.chat.route.service

import com.xxy.chat.common.message.MessageInfo
import kotlinx.coroutines.flow.Flow

interface ManageService  {
    fun stream(connId:String): Flow<MessageInfo>
}