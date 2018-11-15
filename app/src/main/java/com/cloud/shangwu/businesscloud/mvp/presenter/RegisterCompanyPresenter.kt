package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterCompanyContract
import com.cloud.shangwu.businesscloud.mvp.model.RegisterCompanyModel


/**
 * Created by chenxz on 2018/6/3.
 */
class RegisterCompanyPresenter : BasePresenter<RegisterCompanyContract.View>(), RegisterCompanyContract.Presenter {

    override fun registerCompany(companyname: String, password: String,area: String, pid: Int, type: Int,email: String,position: String,username: String) {
        mView?.showLoading()
        val disposable = registerModel.register(companyname, password, area,pid,type,email,position,username)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.apply {
                        if (results.code == 200) {
                            showError(results.message)
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

    private val registerModel by lazy {
        RegisterCompanyModel()
    }



}