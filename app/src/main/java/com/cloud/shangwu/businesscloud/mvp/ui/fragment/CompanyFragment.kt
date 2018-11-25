package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.LablesActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import kotlinx.android.synthetic.main.fragment_company.*


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

    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.ll_user_homepage ->{

            }

            R.id.ll_invite_contact ->{

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