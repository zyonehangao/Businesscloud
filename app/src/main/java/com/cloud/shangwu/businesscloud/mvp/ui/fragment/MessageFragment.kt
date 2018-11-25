package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment

class MessageFragment : BaseFragment() {

    companion object {
        fun getInstance(): MessageFragment = MessageFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_message

    override fun initView() {
    }

    override fun lazyLoad() {

    }
}