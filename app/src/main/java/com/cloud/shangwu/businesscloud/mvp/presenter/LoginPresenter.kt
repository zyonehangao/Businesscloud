package com.cloud.shangwu.businesscloud.mvp.presenter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.im.utils.chat.ChatHelper
import com.cloud.shangwu.businesscloud.mvp.contract.LoginContract
import com.cloud.shangwu.businesscloud.mvp.model.LoginModel
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.model.QBUser

/**
 * Created by chengxiaofen on 2018/5/27.
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    override fun login(username: String, password: String, invitedCode: String) {
        mView?.showLoading()
        val disposable = loginModel.login(username, password, invitedCode)
                .retryWhen(RetryWithDelay())
                .subscribe({ res ->
                    mView?.apply {
                        if (res.code != Constant.OK) {
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


     fun combineLogin(username: String, password: String, invitedCode: String,activity: Activity) {
        loginModel.login(username, password, invitedCode).retryWhen(RetryWithDelay()).doOnSubscribe {
            mView?.showLoading()
        }.doFinally {
            mView?.hideLoading()
        }.subscribe({res->
            if (res.code==Constant.OK){
                _login(res.data,activity)
            }else{
               mView?.apply {
                   showError(res.message)
                   loginFail()
               }
            }
        }, {
            mView?.showError(ExceptionHandle.handleException(it))
        })

    }

    private fun _login(data: LoginData,activity: Activity) {
        mView?.showLoading()
        val user = QBUser(data.username, data.password)
        ChatHelper.getInstance().login(user, object : QBEntityCallback<Void?> {
            override fun onError(p0: QBResponseException?) {
                mView?.hideLoading()
                mView?.loginFail()
            }

            override fun onSuccess(p0: Void?, p1: Bundle?) {
                mView?.hideLoading()
                mView?.loginSuccess(data)
            }
        },activity)
    }
}