package com.xxy.chat.common.bean.route

data class RouteConnInfo(
    val connId:String,
    val connType:
)

enum class RouteConnType(
    val code:Int,
    val desc:String
){

}