package com.xxy.chat.conn.dao

import com.xxy.chat.conn.entity.MessageLastReadEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MessageLastReadDao: ReactiveMongoRepository<MessageLastReadEntity, String>