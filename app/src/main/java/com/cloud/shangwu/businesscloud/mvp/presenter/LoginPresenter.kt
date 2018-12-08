package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.R.string.username
import com.cloud.shangwu.businesscloud.mvp.contract.LoginContract
import com.cloud.shangwu.businesscloud.mvp.model.LoginModel
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay

/**
 * Created by chengxiaofen on 2018/5/27.
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    override fun login(username: String, password: String, invitedCode: String) {
        mView?.showLoading()
        val disposable = loginModel.login(username, password,invitedCode)
                .retryWhen(RetryWithDelay())
                .subscribe({ res ->
                    mView?.apply {
                        if (res.code !=Constant.OK) {
                            showError(res.message)
                            loginFail()
                        } else {
                            loginSuccess(res.data)
                        }

                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(disposable)
    }


    private val loginModel: LoginModel by lazy {
        LoginModel()
    }


}