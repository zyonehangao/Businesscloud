package com.cloud.shangwu.businesscloud.mvp.presenter

import android.util.Log
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.contract.MainContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.Friend
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {


    override fun logout() {

    }
}