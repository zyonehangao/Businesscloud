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

    override fun register(username: String,
                          type: String,
                          telephone: String,
                          password: String,
                          area: String,
                          email: String,
                          position: String,
                          portrait: String,
                          personalCode: String,
                          label: String,
                          invitedCode: String,
                          intro: String,
                          impact: String,
                          hobbys: String,
                          goal: String,
                          companyName: String,
                          clan: String,
                          businessScope: String) {
        mView?.showLoading()
        val disposable = registerModel.register(username,
                type,
                telephone,
                password,
                area,
                email,
                position,
                portrait,
                personalCode,
                label,
                invitedCode,
                intro,
                impact,
                hobbys,
                goal,
                companyName,
                clan,
                businessScope)
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