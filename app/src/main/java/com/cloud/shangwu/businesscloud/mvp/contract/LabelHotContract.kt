package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.LabelHot
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import java.io.File

/**
 * Created by chenxz on 2018/6/3.
 */
interface LabelHotContract {

    interface View : IView {

        fun labelSuccess(data: MutableList<LabelHot>)
        fun labelFail()

        fun labelsaveSuccess()
        fun labelsaveFail()

    }

    interface Presenter : IPresenter<View> {

        fun labelHot(countryId  :Int,type: Int)

        fun saveLabel(countryId  :Int,lid:Int,context:String,ishot:Int,id:Int,status:Int,type: Int)
    }

}