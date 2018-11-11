package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.squareup.moshi.Json


data class HttpResult<T>(@Json(name = "data") val data: T,
                        @Json(name = "errorCode") val errorCode: Int,
                        @Json(name = "errorMsg") val errorMsg: String)

// 登录数据
data class LoginData(
        @Json(name = "chapterTops") val chapterTops: MutableList<String>,
        @Json(name = "collectIds") val collectIds: MutableList<String>,
        @Json(name = "email") val email: String,
        @Json(name = "icon") val icon: String,
        @Json(name = "id") val id: Int,
        @Json(name = "password") val password: String,
        @Json(name = "token") val token: String,
        @Json(name = "type") val type: Int,
        @Json(name = "username") val username: String

)
