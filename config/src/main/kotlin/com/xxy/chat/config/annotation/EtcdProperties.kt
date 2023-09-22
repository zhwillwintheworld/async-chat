package com.xxy.chat.config.annotation
@Target(AnnotationTarget.CLASS,AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class EtcdProperties(
    val path: String
)
