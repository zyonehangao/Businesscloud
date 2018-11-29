package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterPersonalContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPersonalPresenter
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import org.greenrobot.eventbus.EventBus

class MainCompanyActivity : BaseSwipeBackActivity() {

    override fun attachLayoutRes(): Int = R.layout.activity_maincompany

    override fun useEventBus(): Boolean = false

    override fun enableNetworkTip(): Boolean = false

    override fun initData() {

    }

    override fun initView() {

    }

    override fun start() {

    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> {

            }
            R.id.ll_choice_location -> {

            }
        }
    }


}