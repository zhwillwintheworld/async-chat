package com.xxy.chat.conn.dao

import com.xxy.chat.conn.entity.MessageRecordEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRecordDao: ReactiveMongoRepository<MessageRecordEntity, String>