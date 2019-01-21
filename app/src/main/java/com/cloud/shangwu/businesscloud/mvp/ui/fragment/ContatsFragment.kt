package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.widget.LetterView
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.cloud.shangwu.businesscloud.mvp.model.bean.Contact
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.CreatGroupActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.RegisterActivity
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ChooseContactAdapter
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ContactAdapter
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.widget.DividerItemDecoration

import kotlinx.android.synthetic.main.fragment_contant.*
import java.util.ArrayList


class ContatsFragment : BaseFragment() {

//    private var contactNames: Array<String>? = null
    private var layoutManager: LinearLayoutManager? = null
    private var mAdapter: ContactAdapter? = null
    private var contact :MutableList<Contact>?=null


    private val EXTRA_QB_DIALOG = "qb_dialog"

    companion object {
        fun getInstance(): ContatsFragment = ContatsFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_contant

    override fun initView() {
//        contactNames = arrayOf("张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123", "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清", "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之", "林远图", "灭绝师太", "段誉", "鸠摩智")
        contact= mutableListOf();
        layoutManager = LinearLayoutManager(activity)
        loadUsersFromQb()


    }

    override fun lazyLoad() {

    }

    private fun loadUsersFromQb() {


    }

}