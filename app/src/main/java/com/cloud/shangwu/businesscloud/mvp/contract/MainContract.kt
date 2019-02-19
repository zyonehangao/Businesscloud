package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView

/**
 * @author chengxiaofen
 * @date 2018/11/7
 * @desc
 */
interface MainContract {

    interface View : IView {
        fun showLogoutSuccess(success: Boolean)

    }

    interface Presenter : IPresenter<View> {

        fun logout()


    }

}