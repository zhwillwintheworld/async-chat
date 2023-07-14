package com.xxy.chat.web.service

import com.xxy.chat.common.bean.Result
import com.xxy.chat.common.bean.UserLoginInfo
import com.xxy.chat.web.param.UserLoginParam

interface LoginService {
    suspend fun loginByAccount(param: UserLoginParam): Result<UserLoginInfo>
}