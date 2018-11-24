package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.constant.Constant.OK
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.LabelContract
import com.cloud.shangwu.businesscloud.mvp.contract.LabelHotContract
import com.cloud.shangwu.businesscloud.mvp.model.LabelHotModel
import com.cloud.shangwu.businesscloud.mvp.model.LabelModel
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by chenxz on 2018/6/3.
 */
class LabelHotPresenter : BasePresenter<LabelHotContract.View>(), LabelHotContract.Presenter {


    private val labelModel by lazy {
        LabelHotModel()
    }

    override fun labelHot(countryId  :Int,type: Int) {
        mView?.showLoading()
        val disposable = labelModel.label(countryId,type)
                .retryWhen(RetryWithDelay())
                .subscribe({ results ->
                    mView?.apply {
                        if (results.code != OK) {
                            showError(results.message)
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