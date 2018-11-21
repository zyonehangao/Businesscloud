package com.cloud.shangwu.businesscloud.mvp.presenter

import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.mvp.contract.MainContract

class MainPresenter :BasePresenter<MainContract.View>() ,MainContract.Presenter{
    override fun logout() {

    }
}