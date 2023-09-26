package com.xxy.chat.route

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.xxy.chat")
class ChatRouteApplication

fun main(args:Array<String>) {
    runApplication<ChatRouteApplication>(*args)
}
