package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.content.Intent
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.LablesActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import kotlinx.android.synthetic.main.fragment_company.*
import android.text.Spanned
import android.text.style.ImageSpan
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.opengl.ETC1.getWidth
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.IntCompanyActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainCompanyActivity


class CompanyFragment : BaseFragment() , View.OnClickListener {
    override fun lazyLoad() {

    }


    companion object {
        fun getInstance(): CompanyFragment = CompanyFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_company

    override fun initView() {
        ll_user_homepage.setOnClickListener(this)
        ll_invite_contact.setOnClickListener(this)
        ll_cooperation_mycom.setOnClickListener(this)
        ll_follow_mycom.setOnClickListener(this)
        ll_bankcard_mycom.setOnClickListener(this)
        ll_setting_mycom.setOnClickListener(this)

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

    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.ll_user_homepage ->{
                Intent(activity, MainCompanyActivity::class.java).apply {
                    startActivity(this)
//                    startActivity(this)
                }
            }

            R.id.ll_invite_contact ->{
                Intent(activity, IntCompanyActivity::class.java).apply {
                    startActivity(this)
//                    startActivity(this)
                }

            }

            R.id.ll_cooperation_mycom ->{

            }

            R.id.ll_follow_mycom ->{
                JumpUtil.Next(activity!!, LablesActivity::class.java)
            }

            R.id.ll_bankcard_mycom ->{

            }

            R.id.ll_setting_mycom ->{

            }
        }
    }
}