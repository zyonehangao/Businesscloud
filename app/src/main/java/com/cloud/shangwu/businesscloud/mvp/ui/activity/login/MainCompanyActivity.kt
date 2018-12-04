package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.mvp.model.bean.PostData
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.PostAdapter
import kotlinx.android.synthetic.main.activity_choose_hobbies.*
import kotlinx.android.synthetic.main.activity_maincompany.*
import kotlinx.android.synthetic.main.toolbar.*

class MainCompanyActivity : BaseSwipeBackActivity() {
    private var mList: MutableList<PostData>? = null

    override fun attachLayoutRes(): Int = R.layout.activity_maincompany

    override fun useEventBus(): Boolean = false

    override fun enableNetworkTip(): Boolean = false

    override fun initData() {
        mList= mutableListOf()
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
        mList!!.add(PostData("Android","阿里巴巴","北京 排故 1单元","不限","本科","20K"))
    }

    override fun initView() {
        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.floor_mycom)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }


        name.setText("阿里巴巴网络技术有限公司")
        name.post(Runnable {
            //获取第一行的宽度
            val lineWidth = name.getLayout().getLineWidth(0)
            //获取第一行最后一个字符的下标
            val lineEnd = name.getLayout().getLineEnd(0)
            //计算每个字符占的宽度
            val widthPerChar = lineWidth / (lineEnd + 1)
            //计算TextView一行能够放下多少个字符
            val numberPerLine = Math.floor((name.getWidth() / widthPerChar).toDouble()).toInt()
            //在原始字符串中插入一个空格，插入的位置为numberPerLine - 1
            val stringBuilder = StringBuilder("阿里巴巴网络技术有限公司").insert(numberPerLine - 1, " ")

            //SpannableString的构建
            val spannableString = SpannableString(stringBuilder.toString() + " ")
            val drawable = resources.getDrawable(R.drawable.v1_mycom)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
            spannableString.setSpan(imageSpan, spannableString.length - 1, spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            name.setText(spannableString)
        })

        more.setOnClickListener(onClickListener)
        rv_pisitoon.layoutManager=LinearLayoutManager(this)
        rv_pisitoon.adapter=PostAdapter(mList)
    }

    override fun start() {

    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> {

            }
            R.id.ll_choice_location -> {

            }
        }
    }


}