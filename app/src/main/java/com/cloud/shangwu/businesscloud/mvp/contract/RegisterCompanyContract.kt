package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData

/**
 * Created by chenxz on 2018/6/3.
 */
interface RegisterCompanyContract {

    interface View : IView {

        fun registerSuccess(data: LoginData)

        fun registerFail()
    }

    interface Presenter : IPresenter<View> {

        fun registerCompany(username: String, password: String,area: String, pid: Int, type: Int,email: String)

    }

}