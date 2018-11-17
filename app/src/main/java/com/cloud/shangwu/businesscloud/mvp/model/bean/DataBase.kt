package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.cloud.shangwu.businesscloud.R.id.label
import com.squareup.moshi.Json
import java.io.Serializable


data class HttpResult<T>(@Json(name = "data") var data: T,
                         @Json(name = "code") var code: Int,
                         @Json(name = "message") var message: String)

data class DataBaseResult<T>(@Json(name = "data") var data: String,
                         @Json(name = "code") var code: Int,
                         @Json(name = "message") var message: String)

data class BaseResult<T>(@Json(name = "data") var data: MutableList<T>,
                         @Json(name = "code") var code: Int,
                         @Json(name = "message") var message: String)

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
data class UserRegise(var username: String,var  password: String,var  email: String, var invitecode: String, var position: String):Serializable

data class ComRegise(var area : String, var companyName: String, var email : String, var businessScope: String,
                     var goal : String, var intro: String, var invitedCode : String, var  password : String, var  position: String, var type : Int, var username : String):Serializable




