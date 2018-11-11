package com.cloud.shangwu.businesscloud.ui.activity

import android.content.Intent
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPresenter
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.UserRegisterActivity
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import kotlinx.android.synthetic.main.activity_registerpersonal.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

class RegisterPersonalActivity : BaseSwipeBackActivity(), RegisterContract.View {

    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    /**
     * Presenter
     */
    private val mPresenter by lazy {
        RegisterPresenter()
    }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.register_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun registerSuccess(data: LoginData) {
        showToast(getString(R.string.register_success))
        isLogin = true
        user = data.username
        pwd = data.password

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun registerFail() {
        isLogin = false
    }

    override fun attachLayoutRes(): Int = R.layout.activity_registerpersonal

    override fun useEventBus(): Boolean = false

    override fun enableNetworkTip(): Boolean = false

    override fun initData() {
    }

    override fun initView() {
        mPresenter.attachView(this)

        toolbar.run {
            title=""
            toolbar_nam.run {
                text=getString(R.string.register_personal)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        btn_register.setOnClickListener(onClickListener)
        btn_register.setOnClickListener(onClickListener)
        ll_choice_location.setOnClickListener(onClickListener)
    }

    override fun start() {

    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> {
                Intent(this@RegisterPersonalActivity,UserRegisterActivity::class.java).run {
                    startActivitys(this)
                }
            }
            R.id.btn_register -> {
                Intent(this@RegisterPersonalActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.ll_choice_location->{

            }
        }
    }

    /**
     * Register
     */
    private fun register() {
        if (validate()) {
            mPresenter.register(et_username.text.toString(),
                    et_password.text.toString(),
                    et_password2.text.toString(),"","","","","","","","","","","","","","","")
        }
    }

    /**
     * check data
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()
        val password2: String = et_password2.text.toString()
        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (password2.isEmpty()) {
            et_password2.error = getString(R.string.confirm_password_not_empty)
            valid = false
        }
        if (password != password2) {
            et_password2.error = getString(R.string.password_cannot_match)
            valid = false
        }
        return valid
    }

}
