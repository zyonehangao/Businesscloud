package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.Friend
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData

/**
 * Created by Administrator on 2019/2/19.
 */
class ContactsFragmentContract {

    interface View : IView {

        fun onGetFriends(list: MutableList<Friend>)

        fun onError()

    }

    interface Presenter : IPresenter<View> {

        fun getFriends(uid:Int)

    }

}