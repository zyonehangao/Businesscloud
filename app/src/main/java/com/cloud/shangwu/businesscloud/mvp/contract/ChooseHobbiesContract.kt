package com.cloud.shangwu.businesscloud.mvp.contract

import com.cloud.shangwu.businesscloud.base.IPresenter
import com.cloud.shangwu.businesscloud.base.IView
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData

interface ChooseHobbiesContract {

    interface  View :IView{
        fun getListTypeOK(list: List<ChooseHobbiseData.DataBean.ChildrenBeanX>?)
        fun addHobbies(data:String)
    }
    interface  Presenter :IPresenter<View>{
        fun getListType()

        fun getList(list:List<List<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean>>)
    }
}