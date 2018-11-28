package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseActivity

import com.cloud.shangwu.businesscloud.ui.activity.LoginActivity
import com.cloud.shangwu.businesscloud.ui.activity.RegisterPersonalActivity
import com.cloud.shangwu.businesscloud.utils.JumpUtil

import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity() {


    override fun attachLayoutRes(): Int = R.layout.activity_register

    override fun useEventBus(): Boolean = false

    override fun enableNetworkTip(): Boolean = false

    override fun initData() {
    }

    override fun initView() {
        back.setOnClickListener(onClickListener)
        cb_personal.setOnClickListener(onClickListener)
        cb_company.setOnClickListener(onClickListener)
        tv_loginnow.setOnClickListener(onClickListener)
        tv_useragree.setOnClickListener(onClickListener)
    }

    override fun start() {
        if (isLogin){
            JumpUtil.Next(this,LoginActivity::class.java)
        }
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.cb_personal -> {
                Intent(this@RegisterActivity, RegisterPersonalActivity::class.java).apply {
                    startActivity(this)
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.cb_company -> {
                Intent(this@RegisterActivity, RegisterCompanyActivity::class.java).apply {
                    startActivity(this)
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.tv_loginnow ->{
                Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.tv_useragree ->{
                Intent(this@RegisterActivity, UserAgreementActivity::class.java).apply {
                    startActivity(this)
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.back ->{
                finish()
            }
        }
    }

}