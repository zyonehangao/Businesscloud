package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean

interface ToDoListContract {
    interface View : IView {
        fun getList(data: ToDoListBean,isRrefrsh: Boolean)
        fun getError()

        //是否同意添加好友
        fun  addFriends(message: Int);
    }


    interface Presenter : IPresenter<View> {
        fun getToDoList(page: Int, size: Int, uid: String, isRrefrsh: Boolean)

        fun  addFriends(uid :String,fuid:Int,status: Int)
    }
}