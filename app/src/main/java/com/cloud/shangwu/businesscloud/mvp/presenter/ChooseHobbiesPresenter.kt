package com.cloud.shangwu.businesscloud.mvp.presenter

import android.util.Log
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.ChooseHobbiesContract
import com.cloud.shangwu.businesscloud.mvp.model.ChooseHobbiesModel
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import com.google.gson.Gson
import com.orhanobut.logger.Logger

class ChooseHobbiesPresenter : BasePresenter<ChooseHobbiesContract.View>(), ChooseHobbiesContract.Presenter {
    private val chooseHobbiesModel:ChooseHobbiesModel by lazy {
        ChooseHobbiesModel()
    }
    var list = ArrayList<ChooseHobbiseData.DataBean.ChildrenBeanX>()
    var grouplist=ArrayList<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean>()
//    var childData=ChooseHobbiseData.DataBean.ChildrenBeanX
    override fun getListType() {
        mView?.showLoading()
        val subscribe = chooseHobbiesModel.chooseAll()
                .compose(SchedulerUtils.ioToMain())
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.run {
                        if (results.getCode() == Constant.OK) {
                            list = ArrayList()
                            Log.i("getData",Gson().toJson(results.getData()))
                            val data = results.getData()
                            data?.forEachIndexed { index, dataBean ->
                                list.add(ChooseHobbiseData.DataBean.ChildrenBeanX(
                                        ChooseHobbiseData.DataBean.ChildrenBeanX.Heard,
                                        dataBean.hid, dataBean.higher, dataBean.content,
                                        dataBean.label,grouplist
                                ))
                                dataBean.children?.forEachIndexed { indexs, childrenBeanX ->
                                    list.add(ChooseHobbiseData.DataBean.ChildrenBeanX(
                                            ChooseHobbiseData.DataBean.ChildrenBeanX.Text,
                                            childrenBeanX.hid,childrenBeanX.higher,childrenBeanX.content,
                                            childrenBeanX.label,childrenBeanX.children
                                    ))
                                }
                            }
                            Logger.i("ChooseHobbiesPresenter",list.size)
                            getListTypeOK(list)
                        } else {
                            showError(results.getMessage()!!)
                        }
                        hideLoading()
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(subscribe)
    }
}