package com.cloud.shangwu.businesscloud.mvp.contract

import android.app.Activity
import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity
import java.io.File

interface MineContract {
    interface View : IView{
        fun showData()
        fun getArea(tx:String)

        fun JsonDateOk(json:String)
        fun JsonDateErr()
    }

    interface  Presenter : IPresenter<View> {
        fun getData()

        fun getJsonData(activity: MainActivity)

        fun showPickerView(activity: MainActivity)
        fun  upload(file:File)
    }
}