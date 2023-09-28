package com.xxy.chat.web.service.impl

import com.alibaba.fastjson2.JSONObject
import com.xxy.chat.common.bean.api.Result
import com.xxy.chat.common.bean.api.UserLoginInfo
import com.xxy.chat.common.exception.BizException
import com.xxy.chat.common.exception.ErrorConstant
import com.xxy.chat.common.util.Md5Util
import com.xxy.chat.web.dao.UserAccountDao
import com.xxy.chat.web.dao.UserDao
import com.xxy.chat.web.param.UserLoginParam
import com.xxy.chat.web.service.LoginService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class LoginServiceImpl(
    private val userAccountDao: UserAccountDao,
    private val userDao: UserDao,
    private val redisTemplate: ReactiveRedisTemplate<String, String>
) : LoginService {

    override suspend fun loginByAccount(param: UserLoginParam): Result<UserLoginInfo> {
        if (param.accountName == null || param.password == null) {
            throw BizException(ErrorConstant.PARAM_LENGTH_ERROR)
        }
        val userAccount =
            userAccountDao.findByAccountNameAndPassword(param.accountName!!).firstOrNull()
                ?: throw BizException(ErrorConstant.USER_ACCOUNT_NOT_EXIST)
        if (userAccount.password != param.password) {
            throw BizException(ErrorConstant.USER_ACCOUNT_PASSWORD_ERROR)
        }
        val user = userDao.findById(userAccount.userId!!) ?: throw BizException(ErrorConstant.USER_ACCOUNT_NOT_EXIST)
        val token = Md5Util.encode("${user.id}-${LocalDateTime.now().nano}")
        val loginInfo = user.transfer(token)
        redisTemplate.opsForValue().set(token, JSONObject.toJSONString(loginInfo), Duration.ofHours(1)).awaitFirst()
        return Result.ok(loginInfo)
    }
}