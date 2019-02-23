package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean

interface AddFriendsContract {
    interface View : IView {
        fun searchFriends(data: ToDoListBean,isrefesh :Boolean)

        fun sendFriendMessage(code : Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 发送好友添加关系
         */
        fun sendFriendsMessage(type: String, fuid: String, message: String, uid: String)


        /**
         * 搜索用户
         */
        fun searchFriends(page: Int, message: String, count: Int,isrefesh: Boolean)
    }
}