package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPresenter
import com.cloud.shangwu.businesscloud.ui.activity.LoginActivity
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_registercompany.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Administrator on 2018/11/10.
 */
class RegisterCompanyActivity : BaseSwipeBackActivity(), RegisterContract.View{

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

    private val mutableList : ArrayList<String> ?= null

    private val mPresenter by lazy {
        RegisterPresenter()
    }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.register_ing))
    }

    override fun showLoading() {
        mDialog.show();
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

    override fun initData() {

    }

    override fun initView() {
        mPresenter.attachView(this)
        back.setOnClickListener(onClickListener);
        btn_login.setOnClickListener(onClickListener)
    }

    override fun start() {

    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_registercompany
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                if (getMessage()){
                    Intent(this@RegisterCompanyActivity, RegisterCompanySecActivity::class.java).apply {
                        intent.putStringArrayListExtra("message",mutableList)
//                        intent.putExtra("message",mutableList)
                        startActivity(this)
                    }
                    finish()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }

            }
            R.id.back -> {
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    private fun getMessage() : Boolean{
        var valid = true

//        val booleanList : ArrayList<Boolean> ?= null
        var id = et_id.text.trim().toString()
        var pwd=et_password.text.trim().toString()
        var name=et_username.text.trim().toString()
        var email=et_email.text.trim().toString()
        var area=et_area.text.trim().toString()
        var intcode=et_invcode.text.trim().toString()

        if (!validate(id as String)){
            showError(getString(R.string.toast_error)+getString(R.string.toast_error_id))
            valid=false
        }else if(!validate(pwd as String)){
            showError(getString(R.string.toast_error)+getString(R.string.toast_error_psw))
            valid=false
        }else if (!validate(name as String)){
            showError(getString(R.string.toast_error)+getString(R.string.toast_error_name))
            valid=false
        }else if (!validate(email as String)){
            showError(getString(R.string.toast_error)+getString(R.string.toast_error_email))
            valid=false
        }else if (!validate(area as String)){
            showError(getString(R.string.toast_error)+getString(R.string.toast_error_area))
            valid=false
        }
        if (mutableList != null) {
            mutableList.add(id)
            mutableList.add(pwd as String)
            mutableList.add(name as String)
            mutableList.add(email as String)
            mutableList.add(area as String)
            mutableList.add(intcode as String)
        }
        return valid
//        if (mutableList != null) {
//            mutableList.add(id as String)
//            mutableList.add(pwd as String)
//            mutableList.add(name as String)
//            mutableList.add(email as String)
//            mutableList.add(area as String)
//        }
//
//        if (mutableList != null) {
//            for (i in mutableList.indices) {
//                if (booleanList != null) {
//                    var isvalidate=validate(mutableList.get(1))
//                    booleanList.add(isvalidate)
//                }
//            }
//        }
//
//        if (booleanList != null) {
//            booleanList.contains(false)
//        }

    }

    /**
     * Check UserName and PassWord
     */

    private fun validate(str: String): Boolean {
        var valid = true
        if (str.isEmpty()) {
            valid = false
        }
        return valid

    }


}