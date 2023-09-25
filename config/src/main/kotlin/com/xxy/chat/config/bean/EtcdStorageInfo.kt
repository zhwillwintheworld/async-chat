package com.xxy.chat.config.bean

import java.lang.reflect.Field

data class EtcdStorageInfo (
    val clazz: Class<*>,
    var filed : Field?,
    val flag :Boolean
)