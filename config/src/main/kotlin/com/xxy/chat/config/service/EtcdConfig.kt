package com.xxy.chat.config.service

import io.etcd.jetcd.Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["etcd.endpoints"])
class EtcdConfig {

    @Value("\${etcd.endpoints}")
    lateinit var endpoints :String
    @Bean
    fun getClient():Client{
        return Client.builder().endpoints(endpoints).build()
    }
}