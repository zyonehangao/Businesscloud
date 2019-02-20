package com.cloud.shangwu.businesscloud.mvp.model.bean

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

data class LoginData(
        val area: String,
        val businessScope: Any,
        val clan: Any,
        val companyName: Any,
        val email: String,
        val goal: Any,
        val hobbys: Any,
        val im: Any,
        val impact: Any,
        val intro: Any,
        val invitedCode: Any,
        val label: Any,
        val name: String,
        val personalCode: Any,
        val pid: Int,
        val portrait: Any,
        val position: Any,
        val qq: Any,
        val registerTime: Any,
        val status: Any,
        val telephone: Any,
        val token: String,
        val type: Int,
        val uid: Int,
        val username: String,
        val password: String,
        val userno: Any,
        val wx: Any
) : Serializable


data class UserRegise(var username: String, var password: String, var email: String,
                      var invitecode: String, var position: String) : Serializable

data class ComRegise(var area: String, var companyName: String, var email: String,
                     var businessScope: String, var goal: String, var intro: String,
                     var invitedCode: String, var password: String, var position: String,
                     var type: Int, var username: String, var phone: String) : Serializable

data class LabelHot(
        val context: String,
        val country: Int,
        var select: Boolean = false,

        val createTime: Any,
        val ishot: Int,
        val lid: Int,
        val status: Int,
        val type: Int)

data class PostData(
        val name: String,
        val company: String,
        var location: String,
        val exception: String,
        val edution: String,
        val wage: String)

data class Friend(
        val avatar: String,
        val cid: String,
        val credits: String,
        val friends: String,
        val groups: String,
        val link: String,
        val name: String,
        val role: String,
        val uid: String
)





