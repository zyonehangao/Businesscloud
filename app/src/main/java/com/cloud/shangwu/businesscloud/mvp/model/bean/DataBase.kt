package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.squareup.moshi.Json


data class HttpResult<T>(@Json(name = "data") val data: T,
                        @Json(name = "errorCode") val errorCode: Int,
                        @Json(name = "errorMsg") val errorMsg: String)

// 登录数据
data class LoginData(
        @Json(name = "chapterTops") var chapterTops: MutableList<String>,
        @Json(name = "collectIds") var collectIds: MutableList<String>,
        @Json(name = "email") var email: String,
        @Json(name = "icon") var icon: String,
        @Json(name = "id") var id: Int,
        @Json(name = "password") var password: String,
        @Json(name = "token") var token: String,
        @Json(name = "type") var type: Int,
        @Json(name = "username") var username: String

)


