package com.xxy.chat.web.entity

import com.xxy.chat.common.bean.UserLoginInfo
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(schema = "xxy", value = "user")
data class UserEntity(
    @Id
    val id: Long,
    val username: String,
    val gender: Int,
    var birthday: LocalDateTime,
    var status: Int,
    var avatar: String
){
    fun transfer(token:String):UserLoginInfo{
       return UserLoginInfo(id,username,status,token,avatar)
    }
}

