package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.ForgetPasswordContract
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils

class ForgetPasswordPresenter :BasePresenter<ForgetPasswordContract.View>(),ForgetPasswordContract.Presenter {

    override fun getCode(code: String) {
        mView?.showLoading()
        addSubscription(RetrofitHelper.service.email(code)
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ res ->
                    mView?.apply {
                        if (res.code != Constant.OK) {
                            showError(res.message)
                            mView?.hideLoading()
                        } else {
                            getCodeSuccess()
                        }
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                }))
    }

    override fun Forgetpasd(username: String,password: String, code: String) {
        mView?.showLoading()
        addSubscription(RetrofitHelper.service.forgetpassword(username,password,code)
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ res ->
                    mView?.apply {
                        if (res.code != Constant.OK) {
                            showError(res.message)
                            forgetFail()
                        } else {
                            forgetSuccess(res.data)
                        }
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                }))
    }
}