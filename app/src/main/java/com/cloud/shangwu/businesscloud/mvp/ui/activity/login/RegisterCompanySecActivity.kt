package com.cloud.shangwu.businesscloud.mvp.ui.activity.login


import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.LabelContract
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterCompanyContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.LabelPresenter
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterCompanyPresenter
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.FileUtils
import com.cloud.shangwu.businesscloud.utils.Preference
import kotlinx.android.synthetic.main.activity_registercompanysec.*
import kotlinx.android.synthetic.main.activity_user_register.*
import kotlinx.android.synthetic.main.title_register.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Administrator on 2018/11/11.
 */
class RegisterCompanySecActivity:BaseSwipeBackActivity(), RegisterCompanyContract.View ,LabelContract.View{
    override fun JsonDateOk(json: String) {

    }

    override fun uploadOk(json: String) {

    }

    override fun uploadErr() {

    }

    override fun JsonDateErr() {

    }

    override fun labelSuccess(data: LoginData) {

    }

    override fun labelFail() {

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

    private val mutableList = arrayListOf<String>()

    private val REQUEST_CODE_CHOOSE = 23

    private val mPresenter: RegisterCompanyPresenter by lazy {
        RegisterCompanyPresenter()

    }

    private val mLabelPresenter: LabelPresenter by lazy {
        LabelPresenter()

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
        showToast(errorMsg)
    }


    override fun registerSuccess(data: LoginData) {
        showToast(getString(R.string.register_success))
        finish()
    }

    override fun registerFail() {
        showToast(getString(R.string.register_fail))
        finish()
    }

    override fun attachLayoutRes(): Int= R.layout.activity_registercompanysec;

    override fun initData() {
        val listExtra = intent.getStringArrayListExtra("message")
//        mutableList.add(listExtra.toString())
        showToast(listExtra[0])
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLabelPresenter.attachView(this)
        rl_busnissgoal.setOnClickListener(onClickListener);
        rl_companyint.setOnClickListener(onClickListener);
        rl_position.setOnClickListener(onClickListener);
//        logo.setOnClickListener(onClickListener);
//        btn_register.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener)
        logo.setOnClickListener {
        }
//        toolbar.run {
//            title=""
//
//        }
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
            R.id.rl_busnissgoal -> {
                getLable(-1)
                Intent(this@RegisterCompanySecActivity, LablesActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.rl_companyint -> {
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.rl_position -> {
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    private fun getLable(int: Int) {
        mLabelPresenter.label(int)
    }

    /**
     * Register
     */
    private fun register() {
        mPresenter.registerCompany("zhangsan","123456","china",0,1,"abc@163.com","a","haha")

    }

    override fun onDestroy() {
        mDialog.dismiss()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {


        }
    }
}