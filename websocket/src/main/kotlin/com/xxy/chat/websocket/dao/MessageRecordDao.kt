package com.xxy.chat.websocket.dao

import com.xxy.chat.websocket.entity.MessageRecordEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRecordDao: ReactiveMongoRepository<MessageRecordEntity, String>