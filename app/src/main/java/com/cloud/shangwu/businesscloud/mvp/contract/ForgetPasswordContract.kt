package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData

interface ForgetPasswordContract {

    interface View : IView {

        fun forgetSuccess(data: LoginData)
        fun getCodeSuccess()

        fun forgetFail()

    }

    interface Presenter : IPresenter<View> {

        fun Forgetpasd(username: String, password: String,code:String)

        fun getCode(code: String)

    }
}