package com.xxy.chat.web.dao

import com.xxy.chat.web.entity.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserDao : CoroutineCrudRepository<UserEntity, Long>