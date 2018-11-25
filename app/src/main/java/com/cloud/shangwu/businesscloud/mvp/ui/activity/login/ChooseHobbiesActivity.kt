package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.ChooseHobbiesContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData
import com.cloud.shangwu.businesscloud.mvp.presenter.ChooseHobbiesPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.MultiItemAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_choose_hobbies.*
import kotlinx.android.synthetic.main.toolbar.*

class ChooseHobbiesActivity : BaseSwipeBackActivity(), ChooseHobbiesContract.View {
    internal var list: List<String> = ArrayList()
    override fun getListTypeOK(list: List<ChooseHobbiseData.DataBean.ChildrenBeanX>?) {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val toJson = Gson().toJson(list)
        var adapter = MultiItemAdapter(list)

        mRecyclerView.adapter = adapter
    }

    private val mPresenter: ChooseHobbiesPresenter by lazy {
        ChooseHobbiesPresenter()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {


    }

    override fun showError(errorMsg: String) {

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

    }

    override fun start() {
        mPresenter.getListType()
    }
}