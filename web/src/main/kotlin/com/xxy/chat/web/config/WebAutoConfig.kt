package com.xxy.chat.web.config

import com.xxy.chat.config.annotation.EtcdConfigBean
import com.xxy.chat.config.annotation.EtcdProperties

@EtcdConfigBean
class WebAutoConfig {
    @EtcdProperties("/message")
    lateinit var hello:String

}