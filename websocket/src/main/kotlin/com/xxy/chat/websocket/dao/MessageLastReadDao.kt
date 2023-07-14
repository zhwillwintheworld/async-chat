package com.xxy.chat.websocket.dao

import com.xxy.chat.websocket.entity.MessageLastReadEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MessageLastReadDao: ReactiveMongoRepository<MessageLastReadEntity, String>