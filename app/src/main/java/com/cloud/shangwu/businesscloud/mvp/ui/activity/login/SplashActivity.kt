package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.mvp.contract.LoginContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.LoginPresenter
import com.cloud.shangwu.businesscloud.ui.activity.LoginActivity
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import kotlinx.android.synthetic.main.activity_splash.*
import org.greenrobot.eventbus.EventBus

class SplashActivity : BaseActivity() , LoginContract.View{
    override fun loginFail() {

    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    //是否EventBus 传数据毕传
    override fun useEventBus(): Boolean = true

    override fun showError(errorMsg: String) {
    }

    override fun loginSuccess(data: LoginData) {
        isLogin = true

        EventBus.getDefault().postSticky(LoginEvent(isLogin,data))

        var bundle= Bundle()
        bundle.putSerializable("login",data)

        JumpUtil.Next(this,MainActivity::class.java,bundle)
        finish()
    }
    private val mPresenter: LoginPresenter by lazy {
        LoginPresenter()
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
     * local password
     */

    private var alphaAnimation: AlphaAnimation? = null

    override fun attachLayoutRes(): Int = R.layout.activity_splash


    override fun enableNetworkTip(): Boolean = false

    override fun initData() {
    }

    override fun initView() {
        mPresenter.attachView(this)
        alphaAnimation = AlphaAnimation(0.3F, 1.0F)
        alphaAnimation?.run {
            duration = 2000
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
//                    if (user.isEmpty()&&pwd.isEmpty()){
//                        jumpToRegister()
//                    }
//                    if (user.isNotEmpty()&&pwd.isNotEmpty())
//                    mPresenter.combineLogin(user,pwd,"",this@SplashActivity)else jumpToLogin()
                    jumpToLogin()
                }

                override fun onAnimationStart(p0: Animation?) {
                }
            })
        }
        layout_splash.startAnimation(alphaAnimation)
    }

    private fun jumpToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun start() {

    }



    fun jumpToMain() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    fun jumpToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

}
