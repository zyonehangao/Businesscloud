package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.support.v7.widget.LinearLayoutManager
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.ChooseHobbiesContract
import com.cloud.shangwu.businesscloud.mvp.presenter.ChooseHobbiesPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ChooseHobbiesAdapter
import kotlinx.android.synthetic.main.activity_choose_hobbies.*
import kotlinx.android.synthetic.main.activity_choose_hobbies.view.*
import kotlinx.android.synthetic.main.toolbar.*

class ChooseHobbiesActivity : BaseSwipeBackActivity() , ChooseHobbiesContract.View{

    private val mPresenter: ChooseHobbiesPresenter by lazy {
        ChooseHobbiesPresenter()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }

    override fun getListTypeOK() {
    showToast("测试")
    }

    override fun attachLayoutRes(): Int = R.layout.activity_choose_hobbies

    override fun initData() {

    }

    override fun initView() {
        mPresenter.attachView(this)
        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.hobby)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        recyclerview.layoutManager = LinearLayoutManager(this@ChooseHobbiesActivity)
//        recyclerview.adapter= ChooseHobbiesAdapter()
    }

    override fun start() {
        mPresenter.getListType()
    }
}