package com.xxy.chat.route.config

import io.etcd.jetcd.Client
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RegisterCenter(
    private val client: Client
) {
    private val uuid = UUID.randomUUID().toString()
    fun getRegisterCenterList(){
        client.kvClient.get()
    }
}