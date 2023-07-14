package com.xxy.chat.web.controller

import com.xxy.chat.common.bean.Result
import com.xxy.chat.common.bean.UserLoginInfo
import com.xxy.chat.web.param.UserLoginParam
import com.xxy.chat.web.service.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class LoginController (
    private val loginService: LoginService
) {

    @PostMapping("login")
    suspend fun login(@RequestBody param: UserLoginParam): Flow<Result<UserLoginInfo>>{
        return flowOf(loginService.loginByAccount(param))
    }

}