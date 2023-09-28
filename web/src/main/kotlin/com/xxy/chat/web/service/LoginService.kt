package com.xxy.chat.web.service

import com.xxy.chat.common.bean.api.Result
import com.xxy.chat.common.bean.api.UserLoginInfo
import com.xxy.chat.web.param.UserLoginParam

interface LoginService {
    suspend fun loginByAccount(param: UserLoginParam): Result<UserLoginInfo>
}