package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.mvp.contract.UserRegisterContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.UserRegisterPresenter
import com.cloud.shangwu.businesscloud.utils.FileUtils
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.widget.helper.GifSizeFilter
import com.cloud.shangwu.businesscloud.widget.helper.Glide4Engine
import com.google.gson.Gson
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_user_register.*


class UserRegisterActivity : BaseActivity(), UserRegisterContract.View {
    override fun uploadOk(json: String) {

    }

    override fun uploadErr() {

    }

    override fun JsonDateOk(json: String) {

    }

    override fun JsonDateErr() {
    }

    private val mPresenter: UserRegisterPresenter by lazy {
        UserRegisterPresenter()
    }

    override fun userRegisterOK(data: LoginData) {

    }

    override fun userRegisterEdrr() {
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }

    private val REQUEST_CODE_CHOOSE = 23


    override fun attachLayoutRes(): Int = R.layout.activity_user_register

    override fun initData() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)

        btn_register.setOnClickListener {

        }
        iv_logo.setOnClickListener {
            Matisse.from(this@UserRegisterActivity)
                    .choose(MimeType.ofImage(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(
                            CaptureStrategy(false, "com.cloud.shangwu.businesscloud.fileprovider", "test"))
                    .maxSelectable(1)
                    .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(
                            resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    //                                            .imageEngine(new GlideEngine())  // for glide-V3
                    .imageEngine(Glide4Engine())    // for glide-V4
                    .setOnSelectedListener { uriList, pathList ->
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=$pathList")
                    }
                    .originalEnable(true)
                    .maxOriginalSize(10)
                    .autoHideToolbarOnSingleTap(true)
                    .setOnCheckedListener { isChecked ->
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=$isChecked")
                    }
                    .forResult(REQUEST_CODE_CHOOSE)
        }

    }

    override fun initView() {

        mPresenter.attachView(this)

    }

    override fun start() {
        //爱好
        rl_choose_hobbies.setOnClickListener {
            var bundle= Bundle()
            bundle.putString("","")
            JumpUtil.Next(this@UserRegisterActivity,
                    ChooseHobbiesActivity::class.java,bundle)
        }
        //职业
        rl_labels_please.setOnClickListener{
            JumpUtil.Next(this@UserRegisterActivity,LabelsPleaseActivity::class.java)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            Log.e("OnActivityResult ", Matisse.obtainPathResult(data).toString())

            val fileByPath = FileUtils.getFileByPath(Matisse.obtainPathResult(data)[0])
            fileByPath?.let {
                App.instance
                mPresenter.upload(it)
            }

        }
    }
}