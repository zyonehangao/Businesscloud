package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterCompanyContract
import com.cloud.shangwu.businesscloud.mvp.model.RegisterCompanyModel
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Created by chenxz on 2018/6/3.
 */
class RegisterCompanyPresenter : BasePresenter<RegisterCompanyContract.View>(), RegisterCompanyContract.Presenter {

    override fun registerCompany(companyname: String, password: String,area: String, pid: Int, type: Int,email: String,position: String,username: String,telephone: String) {
        mView?.showLoading()
        val disposable = registerModel.register(companyname, password, area,pid,type,email,position,username,telephone)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.apply {
                        if (results.code != Constant.OK) {
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

    override fun upload(file: File) {
//        val file = File(Environment.getExternalStorageDirectory().absolutePath + file.path)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val photo1part = MultipartBody.Part.createFormData("file", file.name, requestBody)
//        RetrofitHelper.service.uploadFile(photo1part)

        RetrofitHelper.service.uploadFile(photo1part)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ results ->
                    mView?.let {
                        if (results.code != Constant.OK) {
                            it.showError(results.message)
                            it.JsonDateErr()
                        } else {
                            it.JsonDateOk(results.data )
                        }
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
    }

    private val registerModel by lazy {
        RegisterCompanyModel()
    }



}