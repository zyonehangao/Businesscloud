package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView

/**
 * Created by chengxiaofen on 2018/5/27.
 */
interface LoginContract {

    interface View : IView {

        fun loginSuccess(data: LoginData)

        fun loginFail()

    }

    interface Presenter : IPresenter<View> {

        fun login(username: String, password: String)

    }

}