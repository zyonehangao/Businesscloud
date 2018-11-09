package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.mvp.contract.RegisterContract
import com.cloud.shangwu.businesscloud.mvp.model.RegisterModel
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.ext.loge
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay

/**
 * Created by chenxz on 2018/6/3.
 */
class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    private val registerModel by lazy {
        RegisterModel()
    }

    override fun registerWanAndroid(username: String, password: String, repassword: String) {
        mView?.showLoading()
        val disposable = registerModel.registerWanAndroid(username, password, repassword)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.apply {
                        if (results.errorCode != 0) {
                            showError(results.errorMsg)
                            registerFail()
                        } else {
                            registerSuccess(results.data)
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