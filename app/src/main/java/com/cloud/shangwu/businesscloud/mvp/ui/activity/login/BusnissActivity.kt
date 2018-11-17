package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import kotlinx.android.synthetic.main.activity_busniss.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Administrator on 2018/11/17.
 */
class BusnissActivity:BaseSwipeBackActivity(){
    override fun attachLayoutRes(): Int =R.layout.activity_busniss

    override fun initData() {

    }

    override fun start() {

    }

    override fun initView() {

        btn_register.setOnClickListener(mOnclickLitener)
        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.busnissscope)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private val mOnclickLitener= View.OnClickListener{ view ->
        when(view.id){
            R.id.iv_black->{
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.btn_register->{
                var intent= Intent()
                var bundle= Bundle()
                bundle.putString("busniss",et_invcode.text.toString())
                intent.putExtra("busniss",bundle)
                setResult(200,intent)
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

        }
    }
}