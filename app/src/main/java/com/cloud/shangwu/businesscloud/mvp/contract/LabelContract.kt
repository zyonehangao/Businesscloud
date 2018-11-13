package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData

/**
 * Created by chenxz on 2018/6/3.
 */
interface LabelContract {

    interface View : IView {

        fun labelSuccess(data: LoginData)

        fun labelFail()
    }

    interface Presenter : IPresenter<View> {

        fun label(type: Int)

    }

}