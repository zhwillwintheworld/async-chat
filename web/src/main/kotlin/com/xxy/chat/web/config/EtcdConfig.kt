package com.xxy.chat.web.config

import io.etcd.jetcd.Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EtcdConfig {
    @Bean
    fun getClient():Client{
        println("开始连接etcd")
        return Client.builder().endpoints("http://42.192.189.185:2379").build()
    }
}