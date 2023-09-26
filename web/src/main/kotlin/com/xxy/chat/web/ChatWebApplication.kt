package com.xxy.chat.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@SpringBootApplication
@ComponentScan("com.xxy.chat")
class ChatWebApplication

fun main(args: Array<String>) {
    runApplication<ChatWebApplication>(*args)
}
