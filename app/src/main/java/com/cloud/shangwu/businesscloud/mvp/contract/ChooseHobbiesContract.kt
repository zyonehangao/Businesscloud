package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView

interface ChooseHobbiesContract {

    interface  View :IView{
        fun getListTypeOK()
    }
    interface  Presenter :IPresenter<View>{
        fun getListType()
    }
}