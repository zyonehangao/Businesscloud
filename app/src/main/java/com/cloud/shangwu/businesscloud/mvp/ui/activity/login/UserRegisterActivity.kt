package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.R.id.btn_register
import com.cloud.shangwu.businesscloud.R.id.iv_logo
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import kotlinx.android.synthetic.main.activity_user_register.*
import java.io.File

class UserRegisterActivity :BaseSwipeBackActivity(){
    private val RC_CHOOSE_PHOTO = 1
    private val PRC_PHOTO_PICKER = 1
    private val RC_PHOTO_PREVIEW = 2
    override fun attachLayoutRes(): Int = R.layout.activity_user_register;
    private val EXTRA_MOMENT = "EXTRA_MOMENT"
    override fun initData() {

    }

    override fun initView() {
        btn_register.setOnClickListener{

        }

        iv_logo.setOnClickListener {
        }
    }


    override fun start() {

    }




}