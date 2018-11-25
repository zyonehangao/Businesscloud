package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment

class ContatsFragment :BaseFragment() {

    companion object {
        fun getInstance(): ContatsFragment = ContatsFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_message

    override fun initView() {

    }

    override fun lazyLoad() {

    }
}