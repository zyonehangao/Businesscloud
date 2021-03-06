package com.cloud.shangwu.businesscloud.base

/**
 * Created by chengxiaofen on 2018/4/21.
 */
interface IPresenter<in V : IView> {

    fun attachView(mView: V)

    fun detachView()

}