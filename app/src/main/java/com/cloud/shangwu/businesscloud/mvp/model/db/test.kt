package com.cloud.shangwu.businesscloud.mvp.model.db

data class test(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)

data class Data(
    val children: List<Any>,
    val content: String,
    val createTime: Any,
    val did: String,
    val higher: String,
    val label: Any,
    val status: Any
)