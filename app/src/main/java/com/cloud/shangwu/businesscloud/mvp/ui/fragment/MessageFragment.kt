package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment

class MessageFragment : BaseFragment() {

    companion object {
        fun getInstance(): MessageFragment = MessageFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_message

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}