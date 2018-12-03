package com.cloud.shangwu.businesscloud.mvp.ui.activity.mine

import android.text.Editable
import android.text.TextWatcher
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.ext.showToast
import kotlinx.android.synthetic.main.activity_user_present.*
import kotlinx.android.synthetic.main.toolbar.*

class UserPresentActivity :BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_user_present

    override fun initData() {
    }

    override fun initView() {
        toolbar.run {
            title=""
            toolbar_nam.run {
                text=getString(R.string.user_present)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        commit.setOnClickListener {

        }
    }

    override fun start() {
//
        ed_text_content.addTextChangedListener(object :TextWatcher{
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