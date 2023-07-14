package com.xxy.chat.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatWebApplication

fun main(args: Array<String>) {
    runApplication<ChatWebApplication>(*args)
}
