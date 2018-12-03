package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import java.io.File

interface UserRegisterContract {
    interface  View :IView{
        fun userRegisterOK(data: LoginData)
        fun userRegisterEdrr()
        fun JsonDateOk(json:String)
        fun JsonDateErr()
        fun uploadOk(json:String)
        fun uploadErr()


    }

    interface Presenter :IPresenter<View>{

        fun upload(file:File)
        fun userRegister ()
    }
}