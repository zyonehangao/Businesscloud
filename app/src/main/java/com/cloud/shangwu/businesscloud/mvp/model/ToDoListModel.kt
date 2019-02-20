package com.cloud.shangwu.businesscloud.mvp.model

import io.reactivex.Observable
import com.cloud.shangwu.businesscloud.base.BaseModel
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils


class ToDoListModel : BaseModel() {

    fun todoList(page: Int, size: Int, uid: String): Observable<ToDoListBean> {
        return RetrofitHelper.service.toDoList(page, size,uid)
                .compose(SchedulerUtils.ioToMain())
    }
}