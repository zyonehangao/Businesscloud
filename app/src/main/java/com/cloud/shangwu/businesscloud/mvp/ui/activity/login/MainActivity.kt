package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.R.id.toolbar
import com.cloud.shangwu.businesscloud.R.string.username
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.MainContract
import com.cloud.shangwu.businesscloud.mvp.presenter.MainPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.fragment.*
import com.cloud.shangwu.businesscloud.widget.helper.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity(), MainContract.View {
    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_CONTACTS = 0x02
    private val FRAGMENT_DYNAMIC = 0x03
    private val FRAGMENT_MINE = 0x04
    private val PERSONAL = 0x05
    private val COMPANY = 0x06
    private var mIndex = FRAGMENT_HOME
    private var mMessageFragment: MessageFragment? = null
    private var mContatsFragment: ContatsFragment? = null
    private var mDynamicFragment: DynamicFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mCompanyFragment: CompanyFragment? = null

    private var mLoginType=COMPANY
    /**
     * Presenter
     */
    private val mPresenter: MainPresenter by lazy {
        MainPresenter()
    }

    override fun showLogoutSuccess(success: Boolean) {


    }

    //是否EventBus 传数据毕传
    override fun useEventBus(): Boolean = true

    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initData() {

    }

    override fun initView() {
        mPresenter.attachView(this)
        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.register_personal)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = 1
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(event: LoginEvent) {
        if (event.isLogin) {
            event.data

        }

    }

    override fun start() {
    }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.action_home -> {
                showFragment(FRAGMENT_HOME)
                true
            }
            R.id.action_contacts -> {
                showFragment(FRAGMENT_CONTACTS)
                true
            }
            R.id.action_dynamic -> {
                showFragment(FRAGMENT_DYNAMIC)
                true
            }
            R.id.action_mine -> {
                showFragment(FRAGMENT_MINE)
                true
            }
            else -> {
                false
            }
        }
    }


    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when(index){
            FRAGMENT_HOME ->{
                toolbar.run {
                    title = ""
                    toolbar_nam.run {
                        text = getString(R.string.home)
                    }
                    setSupportActionBar(this)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    visibility = View.VISIBLE
                }
                if (mMessageFragment == null) {
                    mMessageFragment = MessageFragment.getInstance()
                    transaction.add(R.id.container, mMessageFragment!!, "message")
                } else {
                    transaction.show(mMessageFragment!!)
                }
            }

            FRAGMENT_CONTACTS ->{
                toolbar.run {
                    title = ""
                    toolbar_nam.run {
                        text = getString(R.string.knowledge_system)
                    }
                    setSupportActionBar(this)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    visibility = View.VISIBLE
                }

                if (mContatsFragment == null) {
                    mContatsFragment = ContatsFragment.getInstance()
                    transaction.add(R.id.container, mContatsFragment!!, "contats")
                } else {
                    transaction.show(mContatsFragment!!)
                }
            }


            FRAGMENT_DYNAMIC ->{
                toolbar.run {
                    title = ""
                    toolbar_nam.run {
                        text = getString(R.string.action_dynamic)
                    }
                    setSupportActionBar(this)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    visibility = View.VISIBLE
                }

                if (mDynamicFragment == null) {
                    mDynamicFragment = DynamicFragment.getInstance()
                    transaction.add(R.id.container, mDynamicFragment!!, "dynamic")
                } else {
                    transaction.show(mDynamicFragment!!)
                }
            }

            FRAGMENT_MINE ->{
                toolbar.run {
                    title = ""
                    toolbar_nam.run {
                        text = getString(R.string.mine)
                    }
                    setSupportActionBar(this)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    visibility = View.GONE
                }
                if(mLoginType==PERSONAL){
                    if (mMineFragment == null) {
                        mMineFragment = MineFragment.getInstance()
                        transaction.add(R.id.container, mMineFragment!!, "mine")
                     } else {
                        transaction.show(mMineFragment!!)
                    }
                }else{
                    if (mCompanyFragment == null) {
                        mCompanyFragment = CompanyFragment.getInstance()
                        transaction.add(R.id.container, mCompanyFragment!!, "mine")
                    } else {
                        transaction.show(mCompanyFragment!!)
                    }
                }

            }
        }
        transaction.commit()
    }


    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mMessageFragment?.let { transaction.hide(it) }
        mContatsFragment?.let { transaction.hide(it) }
        mDynamicFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
        mCompanyFragment?.let { transaction.hide(it) }
    }

}
