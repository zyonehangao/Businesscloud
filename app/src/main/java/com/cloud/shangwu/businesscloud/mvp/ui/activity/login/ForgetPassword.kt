package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.support.v7.widget.Toolbar
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.mvp.contract.ForgetPasswordContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.haotian.shoubei.shoubeicashiapp.utils.CountDownTimerUtils
import kotlinx.android.synthetic.main.activity_forget_passwprd.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ForgetPassword : BaseSwipeBackActivity(), ForgetPasswordContract.View {
//    var countDownTimerUtils = CountDownTimerUtils(this@ForgetPassword, tv_time, 60000, 1000)
    var countDownTimerUtils :CountDownTimerUtils?=null
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }


    override fun forgetSuccess(data: LoginData) {

    }


    override fun forgetFail() {

    }



    override fun attachLayoutRes(): Int = R.layout.activity_forget_passwprd;

    override fun initData() {

    }

    override fun initView() {
        toolbar.run {
            title=""
            toolbar_nam.run {
                text=getString(R.string.forgetpsd)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tv_time.setOnClickListener(onClickListener)
    }

    override fun start() {
        countDownTimerUtils = CountDownTimerUtils(this@ForgetPassword, tv_time, 60000, 1000)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.tv_time -> {
                getCode()
            }
        }
    }



    private fun getCode() {
        countDownTimerUtils?.run {
            start()
            onTick(60000)
            onFinish()
        }

    }
}
