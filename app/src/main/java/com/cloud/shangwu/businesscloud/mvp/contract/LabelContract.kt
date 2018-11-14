package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import java.io.File

/**
 * Created by chenxz on 2018/6/3.
 */
interface LabelContract {

    interface View : IView {

        fun labelSuccess(data: LoginData)

        fun labelFail()

        fun JsonDateOk(json:String)
        fun uploadOk(json:String)
        fun uploadErr()
        fun JsonDateErr()
    }

    interface Presenter : IPresenter<View> {

        fun label(type: Int)
        fun upload(file: File)

    }

}