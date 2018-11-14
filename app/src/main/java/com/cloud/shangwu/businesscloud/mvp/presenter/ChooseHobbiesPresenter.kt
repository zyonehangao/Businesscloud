package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.ChooseHobbiesContract
import com.cloud.shangwu.businesscloud.mvp.model.ChooseHobbiesModel
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiesBen
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils

class ChooseHobbiesPresenter : BasePresenter<ChooseHobbiesContract.View>(), ChooseHobbiesContract.Presenter {
    private val chooseHobbiesModel:ChooseHobbiesModel by lazy {
        ChooseHobbiesModel()
    }
    var list = ArrayList<ChooseHobbiesBen.Children>()
    override fun getListType() {
        mView?.showLoading()
        val subscribe = chooseHobbiesModel.chooseAll()
                .compose(SchedulerUtils.ioToMain())
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.run {
                        if (results.code == Constant.OK) {
                            list = ArrayList()
                            val data = results.data
                            getListTypeOK()
                        } else {
                            showError(results.message)
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