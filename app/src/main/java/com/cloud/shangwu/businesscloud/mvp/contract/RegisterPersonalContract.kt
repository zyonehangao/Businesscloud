package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData

interface RegisterPersonalContract {

    interface  View :IView{
        fun  registerOK(data: LoginData)
        fun  registerFail()

        fun  showPicker(tx:String )
    }

    interface Persenter :IPresenter<View>{

        fun userRegister(usernme: String, password: String, email: String, invitedCode: String, pid: String,type: String, area: String,clan: String, name: String)

        fun getJsonData()
        fun start()
        fun showPickerView()
    }

}