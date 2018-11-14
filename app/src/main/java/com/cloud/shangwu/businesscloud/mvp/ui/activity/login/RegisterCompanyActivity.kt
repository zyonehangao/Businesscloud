package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterPersonalContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPersonalPresenter
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.utils.Validator
import kotlinx.android.synthetic.main.activity_registercompany.*
import kotlinx.android.synthetic.main.title_register.*


/**
 * Created by Administrator on 2018/11/10.
 */
class RegisterCompanyActivity : BaseSwipeBackActivity(), RegisterPersonalContract.View {
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun registerOK(data: LoginData) {

    }

    override fun registerFail() {

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

    private val mutableList = arrayListOf<String>()

    override fun initData() {

    }

    override fun initView() {
        mPresenter.attachView(this)
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
//                    Intent(this@RegisterCompanyActivity, RegisterCompanySecActivity::class.java).apply {
//                        intent.putStringArrayListExtra("message",mutableList)
//                        startActivity(this)
//                    }
//                    val intent:Intent=Intent().apply { this.putStringArrayListExtra("message",mutableList)}
//                    startActivity(intent)
                    var bundle=Bundle()
                    bundle.putStringArrayList("message",mutableList)
                    JumpUtil.Next(this@RegisterCompanyActivity,RegisterCompanySecActivity::class.java,bundle)
                    finish()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
        var pwd=et_password.text.trim().toString()
        var name=et_username.text.trim().toString()
        var email=et_email.text.trim().toString()
//        var area=et_area.text.trim().toString()
        var intcode=et_invcode.text.trim().toString()

//        if (!validate(id)){
//            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_id))
//            valid=false
//        }else if(!validate(pwd)||!Validator.isPassword(pwd)){
//            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_psw))
//            valid=false
//        }else if (!validate(name)){
//            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_name))
//            valid=false
//        }else if (!validate(email)||!Validator.isEmail(email)){
//            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_email))
//            valid=false
//        }
        if (mutableList != null) {
            mutableList.add(id)
            mutableList.add(pwd)
            mutableList.add(name)
            mutableList.add(email)
//            mutableList.add(area)
            mutableList.add(intcode)
        }
        var first = mutableList.first()
        showToast(first)
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


}