package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.content.Intent
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.widget.LetterView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.CreatGroupActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.RegisterActivity
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ChooseContactAdapter
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ContactAdapter
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_contant.*



class ContatsFragment :BaseFragment() {

    private var contactNames: Array<String>? = null
    private var layoutManager: LinearLayoutManager? = null
    private var mAdapter: ContactAdapter? = null
    companion object {
        fun getInstance(): ContatsFragment = ContatsFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_contant

    override fun initView() {
        contactNames = arrayOf("张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123", "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清", "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之", "林远图", "灭绝师太", "段誉", "鸠摩智")

        layoutManager = LinearLayoutManager(activity)
        mAdapter = ContactAdapter(activity, contactNames)

        contact_list.layoutManager=layoutManager
        contact_list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST))
        contact_list.adapter = mAdapter


        letter_view!!.setCharacterListener(object : LetterView.CharacterClickListener {
            override fun clickCharacter(character: String) {
                layoutManager!!.scrollToPositionWithOffset(mAdapter!!.getScrollPosition(character), 0)
            }
            override fun clickArrow() {
                layoutManager!!.scrollToPositionWithOffset(0, 0)
            }
        })

        contact_list.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, CreatGroupActivity::class.java)
            startActivity(intent)
        })

    }

    override fun lazyLoad() {

    }
}