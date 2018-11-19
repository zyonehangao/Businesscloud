package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import kotlinx.android.synthetic.main.activity_useragree.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Administrator on 2018/11/19.
 */
class UserAgreementActivity :BaseSwipeBackActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_useragree

    override fun initData() {
        wb_useragree.loadUrl(Constant.USERAGREE)
        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.user_agree)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun initView() {

    }

    override fun start() {

    }
}