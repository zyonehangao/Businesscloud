package com.cloud.shangwu.businesscloud.mvp.ui.activity.login


import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPresenter
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registercompanysec.*
import kotlinx.android.synthetic.main.title_register.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Administrator on 2018/11/11.
 */
class RegisterCompanySecActivity:BaseSwipeBackActivity(), RegisterContract.View {


    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    /**
     * token
     */
    private var token: String by Preference(Constant.TOKEN_KEY, "")

    private var mutableList : ArrayList<String> ?= null

    private val mPresenter: RegisterPresenter by lazy {
        RegisterPresenter()
    }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.hide()
    }

    override fun showError(errorMsg: String) {

    }


    override fun registerSuccess(data: LoginData) {

    }

    override fun registerFail() {

    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_registercompanysec;
    }

    override fun initData() {
        mutableList=intent.getStringArrayListExtra("message");
    }

    override fun initView() {
        rl_busnissgoal.setOnClickListener(onClickListener);
        rl_companyint.setOnClickListener(onClickListener);
        rl_position.setOnClickListener(onClickListener);
        logo.setOnClickListener(onClickListener);
        btn_register.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener)
        toolbar.run {
            title=""

        }
    }

    override fun start() {

    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> {
               register()
            }
            R.id.back -> {
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    /**
     * Register
     */
    private fun register() {
        mPresenter.register(et_username.text.toString(), et_password.text.toString(), et_password.text.toString(),"","","","","","","","","","","","","","","")

    }
}