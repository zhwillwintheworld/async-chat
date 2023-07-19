package com.xxy.chat.conn.service.impl

import com.xxy.chat.common.message.MessageInfo
import com.xxy.chat.conn.dao.MessageRecordDao
import com.xxy.chat.conn.entity.MessageRecordEntity
import com.xxy.chat.conn.holder.ConnectHolder
import com.xxy.chat.conn.service.MessageService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl : MessageService {
    @Autowired
    private lateinit var recordDao: MessageRecordDao

    override fun stream(userId: Long): Flow<MessageInfo> {
        return ConnectHolder.getStream(userId)
    }

    override suspend fun sendMessage(sender: Long, message: MessageInfo) {
        val data = MessageRecordEntity.transfer(message)
        recordDao.save(data).asFlow().onEach {
            ConnectHolder.getStream(data.sender.userId).emit(data.transfer())
            data.receiver?.let { it1 -> ConnectHolder.getStream(it1.userId).emit(data.transfer()) }
        }.collect()

    }

}