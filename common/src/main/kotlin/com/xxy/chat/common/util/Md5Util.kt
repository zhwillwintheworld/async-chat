package com.xxy.chat.common.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Md5Util {

    companion object {
        private val instance: MessageDigest = MessageDigest.getInstance("MD5")

        fun encode(data: String): String {
            try {
                val digest: ByteArray = instance.digest(data.toByteArray())
                val sb = StringBuffer()
                for (b in digest) {
                    //获取低八位有效值
                    val i: Int = b.toInt() and 0xff
                    //将整数转化为16进制
                    var hexString = Integer.toHexString(i)
                    if (hexString.length < 2) {
                        //如果是一位的话，补0
                        hexString = "0$hexString"
                    }
                    sb.append(hexString)
                }
                return sb.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return "";

        }
    }
}