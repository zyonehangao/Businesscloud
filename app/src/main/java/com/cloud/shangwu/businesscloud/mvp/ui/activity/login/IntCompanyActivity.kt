package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cloud.shangwu.businesscloud.R

import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import kotlinx.android.synthetic.main.activity_intcompony.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Administrator on 2018/11/17.
 */
open class IntCompanyActivity:BaseSwipeBackActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_intcompony

    override fun initData() {

    }

    override fun initView() {

        btn_register.setOnClickListener(mOnclickLitener)
        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.ini_company)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun start() {

    }

    private val mOnclickLitener=View.OnClickListener{ view ->
        when(view.id){
            R.id.iv_black->{
                finish()
            }
            R.id.btn_register->{
                var intent=Intent()
                var bundle= Bundle()
                bundle.putString("intcompany",et_invcode.text.toString())
                intent.putExtra("company",bundle)
                setResult(100,intent)
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

        }
    }
}