package com.xxy.chat.web.dao

import com.xxy.chat.web.entity.UserAccountEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param

interface UserAccountDao : CoroutineCrudRepository<UserAccountEntity, Long> {
    /**
     * 通过帐号名跟密码查询账号是否存在
     */
    @Query("select * from user_account  where account_name = :name ")
    fun findByAccountNameAndPassword(@Param("name")name:String): Flow<UserAccountEntity?>
}