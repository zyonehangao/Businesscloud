package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.app.Dialog
import android.util.Log
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.ext.showToast
import com.donkingliang.labels.LabelsView
import kotlinx.android.synthetic.main.activity_lable.*

/**
 * Created by Administrator on 2018/11/12.
 */
class LableActivity:BaseSwipeBackActivity() {

    private val array = mutableListOf("1aaa","2aaa","3aaa","4aaa","5aaa","6aaa","7aaa","8aaa","9aaa","0aaa")

    override fun attachLayoutRes(): Int = R.layout.activity_lable

    override fun initData() {

    }

    override fun initView() {
//        labels.setLabels(array)
//        //标签的点击监听
//        labels.setOnLabelClickListener(LabelsView.OnLabelClickListener { label, data, position ->
//
//        })
        showDialog()
    }

    private fun showDialog() {

    }

    override fun start() {

    }
}