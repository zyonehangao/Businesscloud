package com.cloud.shangwu.businesscloud.mvp.contract

import android.app.Activity
import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity

interface MineContract {
    interface View : IView{
        fun showData()
    }

    interface  Presenter : IPresenter<View> {
        fun getData()

        fun getJsonData(activity: MainActivity)

        fun showPickerView(activity: MainActivity)
    }
}