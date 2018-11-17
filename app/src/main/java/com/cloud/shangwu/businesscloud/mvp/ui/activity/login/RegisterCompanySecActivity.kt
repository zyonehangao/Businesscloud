package com.cloud.shangwu.businesscloud.mvp.ui.activity.login


import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.R.string.register
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
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import id.zelory.compressor.Compressor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


import kotlinx.android.synthetic.main.activity_registercompanysec.*
import kotlinx.android.synthetic.main.activity_user_register.*

import kotlinx.android.synthetic.main.title_register.*
import java.io.File
import java.util.ArrayList


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

    private var mAlbumFiles: ArrayList<AlbumFile>? = null

    private val mPresenter: RegisterCompanyPresenter by lazy {
        RegisterCompanyPresenter()

    }

    private val mLabelPresenter: LabelPresenter by lazy {
        LabelPresenter()

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
        rl_busnissgoal.setOnClickListener(onClickListener)
        rl_companyint.setOnClickListener(onClickListener)
        rl_position.setOnClickListener(onClickListener)
        back.setOnClickListener(onClickListener)

        logo.setOnClickListener {

            Album.image(this)
                    .multipleChoice()
                    .camera(true)
                    .columnCount(4)
                    .selectCount(1)
                    .checkedList(mAlbumFiles)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .title("相册")
                                    .build()
                    )
                    .onResult { result ->

                        mAlbumFiles = result
                        Log.i("mAlbumFiles", mAlbumFiles!![0].path)
                        val fileByPath = FileUtils.getFileByPath(mAlbumFiles!![0].path)

                        fileByPath?.apply {
                            getPath(fileByPath)

                        }

                    }
                    .onCancel { Toast.makeText(this@RegisterCompanySecActivity, R.string.canceled, Toast.LENGTH_LONG).show() }
                    .start()

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
            R.id.rl_busnissgoal -> {
                getLable(-1)
                Intent(this@RegisterCompanySecActivity, LablesActivity::class.java).apply {
                    startActivity(this)
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.rl_companyint -> {
                Intent(this@RegisterCompanySecActivity, LablesActivity::class.java).apply {
                    startActivity(this)
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.rl_position -> {
                Intent(this@RegisterCompanySecActivity, LablesActivity::class.java).apply {
                    startActivity(this)
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    private fun getLable(int: Int) {
        mLabelPresenter.label(int)
    }
    private fun getPath(photos: File) {
            Log.i("图片大小", "原+${photos.length()}")
            Compressor(this)
                    .compressToFileAsFlowable(photos)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ file ->
                        file?.let {
                            App.instance
                            getPath(file)
                            mPresenter.upload(it)
                        }
                    }, { throwable ->
                        throwable.printStackTrace()
                        showError(throwable.message!!)
                    })
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


}