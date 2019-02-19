package com.cloud.shangwu.businesscloud.mvp.model

import android.util.Log
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.contract.ContactsFragmentContract
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils

/**
 * Created by Administrator on 2019/2/19.
 */
class ContactsFragmentPresenter : BasePresenter<ContactsFragmentContract.View>(), ContactsFragmentContract.Presenter {
    override fun getFriends(uid: Int) {
        RetrofitHelper.service.getFriends(uid).compose(SchedulerUtils.ioToMain()).subscribe({
            if (it.code == 200) {
                mView?.onGetFriends(it.data)
            }
        }, {
            Log.e("MainPresenter","getFriend:", it)
            mView?.onError()
        })
    }
}