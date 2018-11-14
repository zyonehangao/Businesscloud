package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.LabelContract
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterContract
import com.cloud.shangwu.businesscloud.mvp.model.LabelModel

/**
 * Created by chenxz on 2018/6/3.
 */
class LabelPresenter : BasePresenter<LabelContract.View>(), LabelContract.Presenter {

    private val labelModel by lazy {
        LabelModel()
    }

    override fun label(type: Int) {
        mView?.showLoading()
        val disposable = labelModel.label(type)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.apply {
                        if (results.errorCode != 0) {
                            showError(results.errorMsg)
                            labelFail()
                        } else {
                            labelSuccess(results.data)
                        }
                        hideLoading()
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(disposable)
    }


}