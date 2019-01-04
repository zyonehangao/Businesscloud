package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterCompanyContract
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterPersonalContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.ComRegise
import com.cloud.shangwu.businesscloud.mvp.model.bean.Contact
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.model.bean.UserRegise
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterCompanyPresenter

import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPersonalPresenter
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import com.cloud.shangwu.businesscloud.utils.Validator
import kotlinx.android.synthetic.main.activity_registercompany.*
import kotlinx.android.synthetic.main.title_register.*
import org.greenrobot.eventbus.EventBus
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.model.QBUser
import com.quickblox.core.QBEntityCallback
import com.quickblox.users.QBUsers




/**
 * Created by Administrator on 2018/11/10.
 */
class RegisterCompanyActivity : BaseSwipeBackActivity(), RegisterPersonalContract.View, RegisterCompanyContract.View {

    var comRegise: ComRegise?=null

    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    override fun JsonDateErr() {

    }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.register_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.hide()
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }


    override fun registerSuccess(data: LoginData) {
        showToast(getString(R.string.register_success))
        isLogin = true
        pwd=et_password.text.toString()
        EventBus.getDefault().post(LoginEvent(isLogin,data))
        var bundle=Bundle()
        bundle.putSerializable("login",data)

        JumpUtil.Next(this,MainActivity::class.java,bundle)
        finish()

    }

    override fun registerFail() {
        showToast(getString(R.string.register_fail))
    }


    override fun JsonDateOk(json: String) {

    }


    override fun registerOK(data: LoginData) {

    }


    private val mRegisterPresenter: RegisterCompanyPresenter by lazy {
        RegisterCompanyPresenter()

    }

    override fun showPicker(tx: String) {
        tv_location?.run {
            text=tx
            setTextColor(resources.getColor(R.color.Black))
        }

    }

    private val mPresenter: RegisterPersonalPresenter by lazy {
        RegisterPersonalPresenter()
    }

    override fun initData() {

    }

    override fun initView() {
        mPresenter.attachView(this)
        mRegisterPresenter.attachView(this)
        back.setOnClickListener(onClickListener)
        btn_login.setOnClickListener(onClickListener)
        ll_choice_location.setOnClickListener(onClickListener)
    }

    override fun start() {
        mPresenter.getJsonData()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_registercompany

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                if (getMessage()){
                   register()
                }

            }
            R.id.back -> {
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.ll_choice_location -> {
                mPresenter.showPickerView()
                Log.i("test","click")
            }
        }
    }
    /**
     * 获取校验数据
     */

    private fun getMessage() : Boolean{
        var valid = true

        var id = et_id.text.trim().toString()
        var pwd= et_password.text!!.trim().toString()
        var name=et_username.text.trim().toString()
        var email=et_email.text.trim().toString()
        var phone=et_phone.text.toString()

        if (!validate(id)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_id))
            valid=false
        }else if(!validate(pwd)||!Validator.isPassword(pwd)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_psw))
            valid=false
        }else if (!validate(name)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_name))
            valid=false
        }else if (!validate(email)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_email))
            valid=false
        }
        comRegise= ComRegise(tv_location.text.toString(),
                name,
                email,
                "","","",
                et_invcode.text.toString(),
                pwd,"",1,
                id,phone)
        return valid
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

    /**
     * Register
     */
    private fun register() {
        mRegisterPresenter.registerCompany(comRegise!!.companyName, comRegise!!.password, comRegise!!.area, 0,
                comRegise!!.type, comRegise!!.email, comRegise!!.position, comRegise!!.username, comRegise!!.phone)

        val user = QBUser(comRegise!!.username, comRegise!!.password)

        QBUsers.signUp(user).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(user: QBUser, args: Bundle) {
                // success
            }

            override fun onError(error: QBResponseException) {
                // error
            }
        })
    }

    override fun onDestroy() {
        mDialog.dismiss()
        super.onDestroy()
    }


}