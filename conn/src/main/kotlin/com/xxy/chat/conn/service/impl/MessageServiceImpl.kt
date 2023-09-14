package com.xxy.chat.conn.service.impl

import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.conn.dao.MessageRecordDao
import com.xxy.chat.conn.entity.MessageRecordEntity
import com.xxy.chat.conn.holder.ConnectHolder
import com.xxy.chat.conn.service.MessageService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MessageServiceImpl : MessageService {
    @Autowired
    private lateinit var recordDao: MessageRecordDao

    override fun stream(userId: Long): Flow<MessageInfo> {
        return ConnectHolder.getStream(userId)
    }

    override suspend fun sendMessage(sender: Long, message: Mono<MessageInfo>) {
        message
            .map { MessageRecordEntity.transfer(it) }
            .let { recordDao.saveAll(it) }
            .asFlow()
            .onEach {
                ConnectHolder.getStream(it.sender.userId).emit(it.transfer())
                it.receiver?.let { it1 ->
                    if (it1.userId != it.sender.userId) ConnectHolder.getStream(it1.userId).emit(it.transfer())
                }
            }.collect()

    }

}