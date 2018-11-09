package com.cloud.shangwu.businesscloud.base

/**
 * Created by chengxiaofen on 2018/4/21.
 */
interface IView {

    fun showLoading()

    fun hideLoading()

    fun showError(errorMsg: String)

}