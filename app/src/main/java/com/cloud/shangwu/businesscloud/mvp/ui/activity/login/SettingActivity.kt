package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.ui.activity.LoginActivity
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

class SettingActivity : BaseActivity() {

    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    override fun attachLayoutRes(): Int = R.layout.activity_setting

    override fun initData() {
    }

    override fun initView() {

        tv_logout.setOnClickListener {
            logout()
        }
        initToolbar(toolbar, true, "设置")
    }

    override fun start() {
    }

    /**
     * 退出登录 Dialog
     */
    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this@SettingActivity, resources.getString(R.string.logout_ing))
    }

    /**
     * Logout
     */
    private fun logout() {
        DialogUtil.getConfirmDialog(this, resources.getString(R.string.confirm_logout),
                DialogInterface.OnClickListener { _, _ ->
                    Preference.clearPreference()
                    showToast(resources.getString(R.string.logout_success))
                    isLogin = false
                    Intent(this@SettingActivity, LoginActivity::class.java).run {
                        FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                }).show()
    }
}