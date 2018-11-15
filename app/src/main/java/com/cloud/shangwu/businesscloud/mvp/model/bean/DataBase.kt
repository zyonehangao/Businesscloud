package com.cloud.shangwu.businesscloud.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.moshi.Json


data class HttpResult<T>(@Json(name = "data") var data: T,
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
//data class ChooseHobbiseData(
//    val code: Int,
//    val `data`: List<Data>,
//    val message: String
//)
//
//data class Data(
//    val children: List<Children>,
//    val content: String,
//    val createTime: Any,
//    val hid: Int,
//    val higher: Int,
//    val label: Any,
//    val status: Any
//)
//
//data class Children(
//    val children: List<Any>,
//    val content: String,
//    val createTime: Any,
//    val hid: Int,
//    val higher: Int,
//    val label: Any,
//    val status: Any
//)

//data class Data(
//        @Json(name = "children") var children: List<Children>,
//        @Json(name = "content") var content: String,
//        @Json(name = "createTime") var createTime: Any,
//        @Json(name = "hid") var hid: Int,
//        @Json(name = "higher") var higher: Int,
//        @Json(name = "label") var label: Any,
//        @Json(name = "status") var status: Any
//) : MultiItemEntity {
//    override fun getItemType(): Int = higher
//
//    data class Children(
//            @Json(name = "children") var children: List<Any>,
//            @Json(name = "content") var content: String,
//            @Json(name = "createTime") var createTime: Any,
//            @Json(name = "hid") var hid: Int,
//            @Json(name = "higher") var higher: Int,
//            @Json(name = "label") var label: Any,
//            @Json(name = "status") var status: Any
//    ) : MultiItemEntity {
//        override fun getItemType(): Int = higher
//
//        companion object {
//            var Text = 1
//            var Heard = 0
//        }
//    }
//}




