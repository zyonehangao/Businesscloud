package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.R.string.username
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterPersonalContract
import com.cloud.shangwu.businesscloud.mvp.model.UserRegisterModel


class RegisterPersonalPresenter : BasePresenter<RegisterPersonalContract.View>(), RegisterPersonalContract.Persenter {


    private val userRegisterModer: UserRegisterModel by lazy {
        UserRegisterModel()
    }

    override fun userRegister(usernme: String, password: String, email: String, invitedCode: String, portrait: String, hobbys: String, label: String) {
        mView?.showLoading()
        val subscribe = userRegisterModer.UserRegister(usernme, password, email, invitedCode, portrait, hobbys, label)
                .retryWhen(RetryWithDelay())
                .subscribe({ res ->
                    mView?.apply {
                        if (res.errorCode == 0) {
                            showError(res.errorMsg)
                            registerFail()
                        } else {
                            registerOK(res.data)
                        }
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