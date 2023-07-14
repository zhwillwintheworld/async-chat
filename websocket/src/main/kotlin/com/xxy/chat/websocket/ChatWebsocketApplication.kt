package com.xxy.chat.websocket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class ChatWebsocketApplication

fun main(args: Array<String>) {
    runApplication<ChatWebsocketApplication>(*args)
}
