package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.ChooseHobbiesContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.ChooseHobbiesPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.MultiItemAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_choose_hobbies.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ChooseHobbiesActivity : BaseSwipeBackActivity(), ChooseHobbiesContract.View {
    override fun addHobbies(data: String) {
                    intent.putExtra(Constant.TODO_DATA,data)
            setResult(HOBBTY,intent)
            finish()
    }
    override fun useEventBus(): Boolean = true
    internal var list: List<String> = ArrayList()
    var HOBBTY :Int = 1000
    var adapter : MultiItemAdapter ?=null
    var  loginData :LoginData ?=null
    override fun getListTypeOK(list: List<ChooseHobbiseData.DataBean.ChildrenBeanX>?) {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val toJson = Gson().toJson(list)
         adapter = MultiItemAdapter(list)
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

    //获取登录返回的数据
    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    fun onMessageEvent(event: LoginEvent) {
        Log.i("ChooseHobbiesActivity","${event.data}")
        loginData = event.data
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

        btn_confirm.setOnClickListener {
            mPresenter.getList(adapter!!.hobbiesList)

        }
    }

    override fun start() {
        mPresenter.getListType()
    }
}