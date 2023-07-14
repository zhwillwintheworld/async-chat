package com.xxy.chat.common.exception

enum class ErrorConstant (val code:Int,val message:String){
    // basic error

    PARAM_LENGTH_ERROR(300,"缺少必要参数"),
    PARAM_VALUE_ERROR(301,"参数值错误"),


    // 账号相关
    USER_ACCOUNT_NOT_EXIST(1001,"账号不存在"),
    USER_ACCOUNT_PASSWORD_ERROR(1002,"密码错误"),



    ;

}