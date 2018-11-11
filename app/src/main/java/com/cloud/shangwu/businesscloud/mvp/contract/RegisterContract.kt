package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView

/**
 * Created by chenxz on 2018/6/3.
 */
interface RegisterContract {

    interface View : IView {

        fun registerSuccess(data: LoginData)

        fun registerFail()
    }

    interface Presenter : IPresenter<View> {

        fun register(username: String, password: String, repassword: String)

    }

}