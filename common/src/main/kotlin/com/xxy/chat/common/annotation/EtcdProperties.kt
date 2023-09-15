package com.xxy.chat.common.annotation
@Target(AnnotationTarget.CLASS,AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class EtcdProperties(
    val path: String
)
