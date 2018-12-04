package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.cloud.shangwu.businesscloud.R

import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.ext.showToast
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
        ed_text_content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (ed_text_content.text.toString().length>300){
                    showToast("最多只能输入300字")

                    return
                }
            }
            //            tv_textsize
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                tv_textsize.text ="${ed_text_content.text.toString().length}"+"/300"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }



        })
    }


}