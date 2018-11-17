package com.cloud.shangwu.businesscloud.mvp.presenter

import android.os.Environment
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.mvp.contract.UserRegisterContract
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import android.os.Environment.getExternalStorageDirectory


class UserRegisterPresenter : BasePresenter<UserRegisterContract.View>(), UserRegisterContract.Presenter {

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



    override fun userRegister() {

    }



}