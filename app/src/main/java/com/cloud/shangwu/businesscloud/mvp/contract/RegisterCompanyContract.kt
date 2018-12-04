package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import java.io.File

/**
 * Created by chenxz on 2018/6/3.
 */
interface RegisterCompanyContract {

    interface View : IView {

        fun registerSuccess(data: LoginData)

        fun registerFail()

        fun JsonDateErr()

        fun JsonDateOk(json:String)
    }

    interface Presenter : IPresenter<View> {

        fun registerCompany(companyname: String, password: String,area: String, pid: Int, type: Int,email: String,position: String,username: String,telephone: String)

        fun upload(file: File)

    }



}