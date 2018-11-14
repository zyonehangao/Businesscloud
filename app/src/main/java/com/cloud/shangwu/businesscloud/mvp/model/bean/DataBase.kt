package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.moshi.Json
import java.io.Serializable


data class HttpResult<T>(@Json(name = "data") val data: T,
                         @Json(name = "code") val code: Int,
                         @Json(name = "message") val message: String)

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

data class ChooseHobbiesBen(
        val children: List<Children>,
        val content: String,
        val createTime: Any,
        val hid: Int,
        val higher: Int,
        val label: Any,
        val status: Any) : MultiItemEntity {
    override fun getItemType(): Int = higher

    data class Children(
            val children: List<Any>,
            val content: String,
            val createTime: Any,
            val hid: Int,
            val higher: Int,
            val label: Any,
            val status: Any
    ) : MultiItemEntity {
        override fun getItemType(): Int = higher
        companion object {
            val Text = 1
            val Heard = 0
        }
    }
    }



