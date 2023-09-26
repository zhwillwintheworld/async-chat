package com.xxy.chat.route.controller

import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.route.service.ManageService
import kotlinx.coroutines.flow.Flow
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
@MessageMapping("api.manage.v1")
class ManagerController(
    private val manageService: ManageService
) {

    @MessageMapping("stream")
    suspend fun getMessage(@Payload connId:String): Flow<MessageInfo> {
        return manageService.stream(connId)
    }

}