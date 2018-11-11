package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.utils.Validator
import kotlinx.android.synthetic.main.activity_registercompany.*
import kotlinx.android.synthetic.main.title_register.*


/**
 * Created by Administrator on 2018/11/10.
 */
class RegisterCompanyActivity : BaseSwipeBackActivity(){

    private val mutableList : ArrayList<String> ?= null

    override fun initData() {

    }

    override fun initView() {
        back.setOnClickListener(onClickListener);
        btn_login.setOnClickListener(onClickListener)
    }

    override fun start() {

    }

    override fun attachLayoutRes(): Int = R.layout.activity_registercompany

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
    /**
     * 获取校验数据
     */

    private fun getMessage() : Boolean{
        var valid = true

        var id = et_id.text.trim().toString()
        var pwd=et_password.text.trim().toString()
        var name=et_username.text.trim().toString()
        var email=et_email.text.trim().toString()
        var area=et_area.text.trim().toString()
        var intcode=et_invcode.text.trim().toString()

        if (!validate(id)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_id))
            valid=false
        }else if(!validate(pwd)||!Validator.isPassword(pwd)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_psw))
            valid=false
        }else if (!validate(name)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_name))
            valid=false
        }else if (!validate(email)||!Validator.isEmail(email)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_email))
            valid=false
        }else if (!validate(area)){
            showToast(getString(R.string.toast_error)+getString(R.string.toast_error_area))
            valid=false
        }
        if (mutableList != null) {
            mutableList.add(id)
            mutableList.add(pwd)
            mutableList.add(name)
            mutableList.add(email)
            mutableList.add(area)
            mutableList.add(intcode)
        }
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