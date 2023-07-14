package com.xxy.chat.websocket.service.impl

import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.common.message.MessageType
import com.xxy.chat.common.message.UserInfoDTO
import com.xxy.chat.websocket.dao.MessageRecordDao
import com.xxy.chat.websocket.entity.MessageRecordEntity
import com.xxy.chat.websocket.holder.ConnectHolder
import com.xxy.chat.websocket.service.MessageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.reactor.asFlux
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageServiceImpl : MessageService {
    @Autowired
    private lateinit var recordDao: MessageRecordDao

    override fun stream(userId: Long): Flow<MessageInfo> {
        return ConnectHolder.getStream(userId)
            .onStart {
                for (i in 1..20) {
                    delay(1000)
                    this.emit(
                        MessageInfo(
                            "msgid-$i",
                            "$i",
                            MessageType.TEXT,
                            "test-$i",
                            UserInfoDTO(i.toLong(), "zhanghua", "test", false),
                            UserInfoDTO(i.toLong(), "zhanghua", "test", false),
                            null,
                            LocalDateTime.now(),
                            false

                        )
                    )
                }
            }
    }

    override suspend fun sendMessage(sender: Long, message: Flow<MessageInfo>) {
        message
            .map { MessageRecordEntity.transfer(it) }
            .let { recordDao.saveAll(it.asPublisher()) }
            .asFlow()
            .onEach {
                ConnectHolder.getStream(it.sender.userId).emit(it.transfer())
                it.receiver?.let { it1 -> ConnectHolder.getStream(it1.userId).emit(it.transfer()) }
            }.collect()

    }

}