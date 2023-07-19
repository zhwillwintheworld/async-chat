package com.xxy.chat.conn.holder

import com.xxy.chat.common.message.MessageInfo
import kotlinx.coroutines.flow.MutableSharedFlow

class ConnectHolder {

    companion object {
        private val connectHolder: MutableMap<Long, MutableSharedFlow<MessageInfo>> = mutableMapOf()
        fun getStream(userId: Long): MutableSharedFlow<MessageInfo> {
            var connect = connectHolder[userId]
            if (connect == null) {
                connect = MutableSharedFlow()
                connectHolder[userId] = connect
            }
            return connect
        }
    }
}