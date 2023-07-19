package com.xxy.chat.conn.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(value = "MessageLastRead")
data class MessageLastReadEntity(
    @Id
    var id:String?,
    var userId: Long?,
    var lastIndex : String?,
    var updateTime:LocalDateTime?
)
