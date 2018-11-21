package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.support.v7.widget.Toolbar
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.R.string.code
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.ForgetPasswordContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.ForgetPasswordPresenter
import com.cloud.shangwu.businesscloud.mvp.presenter.LoginPresenter
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.haotian.shoubei.shoubeicashiapp.utils.CountDownTimerUtils
import kotlinx.android.synthetic.main.activity_forget_passwprd.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.greenrobot.eventbus.EventBus

class ForgetPassword : BaseSwipeBackActivity(), ForgetPasswordContract.View {

    override fun getCodeSuccess() {
        showToast(getString(R.string.sms_succes))
        getCode()
        hideLoading()
    }

    var countDownTimerUtils :CountDownTimerUtils?=null

    private val mPresenter: ForgetPasswordPresenter by lazy {
        ForgetPasswordPresenter()
    }
    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.record))
    }

    override fun useEventBus(): Boolean = true

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }


    override fun forgetSuccess(data: LoginData) {
        showToast(getString(R.string.forget_password_success))
        isLogin = true
        EventBus.getDefault().post(LoginEvent(isLogin,data))
        JumpUtil.Next(this,MainActivity::class.java)
        finish()
    }


    override fun forgetFail() {
        hideLoading()
    }



    override fun attachLayoutRes(): Int = R.layout.activity_forget_passwprd;

    override fun initData() {

    }

    override fun initView() {
        mPresenter.attachView(this)
        toolbar.run {
            title=""
            toolbar_nam.run {
                text=getString(R.string.forgetpsds)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tv_time.setOnClickListener(onClickListener)
        btn_login.setOnClickListener(onClickListener)
    }

    override fun start() {
        countDownTimerUtils = CountDownTimerUtils(this@ForgetPassword, tv_time, 60000, 1000)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.tv_time -> {
                if (!et_username.text.toString().isEmpty())
                    mPresenter.getCode(et_username.text.toString())
                else showToast(getString(R.string.email))
            }
            R.id.btn_login ->{
                if (et_username.text.toString().isEmpty()) return@OnClickListener showToast(getString(R.string.email))
                if (ed_code.text.toString().isEmpty())return@OnClickListener showToast(getString(R.string.code))
                if (new_password.text.toString().isEmpty()) return@OnClickListener showToast(getString(R.string.email))
                if (password.text.toString().isEmpty()) return@OnClickListener showToast(getString(R.string.password_not_empty))
                if(password.text.toString() != new_password.text.toString()) return@OnClickListener showToast(getString(R.string.password_cannot_match))

                mPresenter.Forgetpasd(et_username.text.toString(),new_password.text.toString(),ed_code.text.toString())
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
