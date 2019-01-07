package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.MainContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.MainPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pop_menu.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R.string.navigation
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.ColorEvent
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.event.MessageEvent
import com.cloud.shangwu.businesscloud.utils.CustomPopWindow
import com.cloud.shangwu.businesscloud.widget.BNVEffect
import kotlin.math.log


class MainActivity : BaseActivity(), MainContract.View {
    private val FRAGMENT_HOME = 0x05
    private val FRAGMENT_CONTACTS = 0x02
    private val FRAGMENT_DYNAMIC = 0x03
    private val FRAGMENT_MINE = 0x04
    private val PERSONAL = 0x00
    private val COMPANY = 0x01
    private var mIndex = FRAGMENT_HOME
    private var mMessageFragment: MessageFragment? = null
    private var mContatsFragment: ContatsFragment? = null
    private var mDynamicFragment: DynamicFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mCompanyFragment: CompanyFragment? = null
    var budle:Bundle?=null
    private var mCustomPopWindow: CustomPopWindow? = null
    private var mLoginType=COMPANY
    private var mLogindata:LoginData?=null
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


        mLogindata = intent.extras.getSerializable("login")as LoginData
        mLoginType= mLogindata!!.type
    }

    override fun initView() {
        mPresenter.attachView(this)


        iv_black.setOnClickListener{
            showPopMenu()
        }
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
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
        showFragment(mIndex)

    }

    private fun showPopMenu() {
        val contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu_item, null)
        //处理popWindow 显示内容
        handleLogic(contentView)
        //创建并显示popWindow
        mCustomPopWindow = CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create()
                .showAsDropDown(iv_black, 50, 20)


    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private fun handleLogic(contentView: View) {
        val listener = View.OnClickListener { v ->
            if (mCustomPopWindow != null) {
                mCustomPopWindow?.dissmiss()
            }
            var showContent = ""
            when (v.id) {
                R.id.menu1 -> showContent = "点击 Item菜单1"
                R.id.menu2 -> showContent = "点击 Item菜单2"
                R.id.menu3 -> showContent = "点击 Item菜单3"
                R.id.menu4 -> showContent = "点击 Item菜单4"
                R.id.menu5 -> showContent = "点击 Item菜单5"
            }

            Toast.makeText(this@MainActivity, showContent, Toast.LENGTH_SHORT).show()
        }
        contentView.findViewById<View>(R.id.menu1).setOnClickListener(listener)
        contentView.findViewById<View>(R.id.menu2).setOnClickListener(listener)
        contentView.findViewById<View>(R.id.menu3).setOnClickListener(listener)
        contentView.findViewById<View>(R.id.menu4).setOnClickListener(listener)
        contentView.findViewById<View>(R.id.menu5).setOnClickListener(listener)
    }

    override fun initColor() {
        super.initColor()
        refreshColor(ColorEvent(true))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent) {
        if (event.isRefresh) {

        }
    }
    //获取登录返回的数据
    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    fun onMessageEvent(event: LoginEvent) {
        Log.i("onMessageEvent","${event.data}")
        budle = Bundle()
        budle!!.putSerializable(Constant.LOGIN_KEY,event.data)
        mLoginType= event.data.run {
           if (1==type) COMPANY else PERSONAL
        }
    }

    override fun start() {

    }
    override fun recreate() {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (mMessageFragment != null) {
                fragmentTransaction.remove(mMessageFragment!!)
            }
            if (mContatsFragment != null) {
                fragmentTransaction.remove(mContatsFragment!!)
            }
            if (mDynamicFragment != null) {
                fragmentTransaction.remove(mDynamicFragment!!)
            }
            if (mMineFragment != null) {
                fragmentTransaction.remove(mMineFragment!!)
            }
            if (mCompanyFragment != null) {
                fragmentTransaction.remove(mCompanyFragment!!)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.recreate()
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
        when (index) {
            FRAGMENT_HOME -> {
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

            FRAGMENT_CONTACTS -> {
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


            FRAGMENT_DYNAMIC -> {
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

            FRAGMENT_MINE -> {
                toolbar.run {
                    title = ""
                    toolbar_nam.run {
                        text = getString(R.string.mine)
                    }
                    setSupportActionBar(this)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    visibility = View.GONE
                }
                if (mLoginType == PERSONAL) {
                    if (mMineFragment == null) {
                        mMineFragment = MineFragment.getInstance(budle!!)
                        transaction.add(R.id.container, mMineFragment!!, "mine")
                    } else {
                        transaction.show(mMineFragment!!)
                    }
                } else {
                    if (mCompanyFragment == null) {
                        mCompanyFragment = CompanyFragment.getInstance()
                        transaction.add(R.id.container, mCompanyFragment!!, "company")
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


    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast(getString(R.string.exit_tip))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        super.onDestroy()
        mMessageFragment = null
        mContatsFragment = null
        mDynamicFragment = null
        mMineFragment = null
        mCompanyFragment = null

    }
}
