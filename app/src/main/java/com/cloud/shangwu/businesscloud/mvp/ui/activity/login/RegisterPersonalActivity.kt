package com.cloud.shangwu.businesscloud.ui.activity

import android.os.Bundle
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterPersonalContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.UserRegise
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPersonalPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.UsersRegisterActivity
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import kotlinx.android.synthetic.main.activity_registerpersonal.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus


class RegisterPersonalActivity : BaseSwipeBackActivity(), RegisterPersonalContract.View {
    override fun showPicker(tx: String) {
        tv_location?.run {
            text=tx
            setTextColor(resources.getColor(R.color.Black))
        }

    }


    override fun registerOK(data: LoginData) {
        showToast(getString(R.string.register_success))
        isLogin = true
        user = data.username
        pwd = data.password

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }


    private val mPresenter: RegisterPersonalPresenter by lazy {
        RegisterPersonalPresenter()
    }

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
            title = ""
            toolbar_nam.run {
                text = getString(R.string.register_personal)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        btn_register.setOnClickListener(onClickListener)
        ll_choice_location.setOnClickListener(onClickListener)
    }

    override fun start() {
        mPresenter.getJsonData()
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> {
                register()
            }
            R.id.ll_choice_location -> {
                mPresenter.showPickerView()
            }
        }
    }

    /**
     * Register
     */
    private fun register() {
        if (validate()) {
           val bundle= Bundle()
            bundle.putSerializable("UserRegise",UserRegise(
                    et_username.text.toString(),
                    et_password.text.toString(),
                    et_password2.text.toString(),
                    invitationcode.text.toString(),
                    tv_location.text.toString()
                    ))
            JumpUtil.Next(this@RegisterPersonalActivity,UsersRegisterActivity::class.java,bundle)
        }
    }

    /**
     * check data
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()
        val email: String = et_password2.text.toString()
        val invitationcode: String = invitationcode.text.toString()
        val tv_location: String = tv_location.text.toString()
        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (email.isEmpty()) {
            et_password.error = getString(R.string.input_emaill)
            valid = false
        }

        if (tv_location.isEmpty()) {
            et_password2.error = getString(R.string.choice_location)
            valid = false
        }
        return valid
    }




}
