package com.cloud.shangwu.businesscloud.ui.activity

import android.content.ComponentName
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.event.MessageEvent
import com.cloud.shangwu.businesscloud.ext.showToast

import com.cloud.shangwu.businesscloud.mvp.contract.LoginContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.LoginPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.ForgetPassword
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity

import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.RegisterActivity

import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import com.inscripts.interfaces.Callbacks
import com.inscripts.interfaces.LaunchCallbacks
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.white_toolbar.*
import org.greenrobot.eventbus.EventBus

import io.reactivex.Single
import org.json.JSONObject


class LoginActivity : BaseActivity(), LoginContract.View {
    override fun initData() {

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
     * token
     */
    private var token: String by Preference(Constant.TOKEN_KEY, "")

    /**
     * token
     */


    private val mPresenter: LoginPresenter by lazy {
        LoginPresenter()
    }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
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

    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun useEventBus(): Boolean = true

    override fun enableNetworkTip(): Boolean = false



    override fun initView() {

        tl_title.run {
            title = ""
            toolbar_withe_name.run {
                text = getString(R.string.login)

            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        mPresenter.attachView(this)
        et_username.setText(user)
        btn_login.setOnClickListener(onClickListener)
        tv_sign_up.setOnClickListener(onClickListener)
        tv_forgetpsd.setOnClickListener(onClickListener)
    }

    override fun start() {

    }

    override fun loginSuccess(data: LoginData) {
        showToast(getString(R.string.login_success))
        isLogin = true
        user = data.username
        pwd = et_password.text.toString()
        token = data.token


        EventBus.getDefault().postSticky(LoginEvent(isLogin, data))

        var bundle = Bundle()
        bundle.putSerializable("login", data)



        JumpUtil.Next(this, MainActivity::class.java, bundle)
        finish()
    }




    override fun loginFail() {
        hideLoading()
    }

    /**
     * OnClickListener
     */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                login()
            }
            R.id.tv_sign_up -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)

            }
            R.id.tv_forgetpsd -> {
                val intent = Intent(this@LoginActivity, ForgetPassword::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * Login
     */
    private fun login() {

        if (validate()) run {

            mPresenter.login(et_username.text.toString(), et_password.text.toString(), invitation_code.text.toString())
            App.cometChat.loginWithUID(this@LoginActivity, "SUPERHERO1", object : Callbacks {
                override fun successCallback(jsonObject: JSONObject) {
                    Log.d("LoginActivity", "Login Success : " + jsonObject.toString())
                    Toast.makeText(this@LoginActivity, jsonObject.toString(), Toast.LENGTH_LONG).show()
                    launchChat()
                }

                override fun failCallback(jsonObject: JSONObject) {
                    Log.d("LoginActivity", "Login Fail : " + jsonObject.toString())
                    Toast.makeText(this@LoginActivity, jsonObject.toString(), Toast.LENGTH_LONG).show()
                }
            })
        }

    }


    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()

        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid

    }

    override fun onDestroy() {
        mDialog.dismiss()
        super.onDestroy()
    }

    /**
     * Launches the chat.
     */
    private fun launchChat() {

        App.cometChat.launchCometChat(this@LoginActivity, true, object : LaunchCallbacks {
            override fun successCallback(jsonObject: JSONObject) {
//                Log.d(TAG, "Launch Success : " + jsonObject.toString())
            }

            override fun failCallback(jsonObject: JSONObject) {
//                Log.d(TAG, "Launch Fail : " + jsonObject.toString())
            }

            override fun userInfoCallback(jsonObject: JSONObject) {
//                Log.d(TAG, "User Info Received : " + jsonObject.toString())
            }

            override fun chatroomInfoCallback(jsonObject: JSONObject) {
//                Log.d(TAG, "Chatroom Info Received : " + jsonObject.toString())
            }

            override fun onMessageReceive(jsonObject: JSONObject) {
//                Log.d(TAG, "Message Received : " + jsonObject.toString())
            }

            override fun error(jsonObject: JSONObject) {
//                Log.d(TAG, "Error : " + jsonObject.toString())
            }

            override fun onWindowClose(jsonObject: JSONObject) {
//                Log.d(TAG, "Chat Window Closed : " + jsonObject.toString())
            }

            override fun onLogout() {
//                Log.d(TAG, "Logout")
            }
        })

    }


}
