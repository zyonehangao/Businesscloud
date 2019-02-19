package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.content.Intent
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
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.ColorEvent
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.event.MessageEvent
import com.cloud.shangwu.businesscloud.im.Keys.BroadCastReceiverKeys
import com.cloud.shangwu.businesscloud.im.activity.GroupsActivity
import com.cloud.shangwu.businesscloud.im.fragment.ContactFragment
import com.cloud.shangwu.businesscloud.im.helpers.CCMessageHelper
import com.cloud.shangwu.businesscloud.im.helpers.CCSubcribe
import com.cloud.shangwu.businesscloud.im.models.Contact
import com.cloud.shangwu.businesscloud.im.models.Groups
import com.cloud.shangwu.businesscloud.im.models.OneOnOneMessage
import com.cloud.shangwu.businesscloud.utils.CustomPopWindow
import com.cloud.shangwu.businesscloud.widget.BNVEffect
import com.google.firebase.messaging.FirebaseMessaging
import com.inscripts.helpers.PreferenceHelper
import com.inscripts.interfaces.SubscribeCallbacks
import com.inscripts.keys.CometChatKeys
import com.inscripts.keys.PreferenceKeys
import com.inscripts.utils.CommonUtils
import com.inscripts.utils.Logger
import com.inscripts.utils.SessionData
import org.json.JSONException
import org.json.JSONObject
import java.util.*
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
//    private var mContatsFragment: ContatsFragment? = null

    private var mContatsFragment: ContactFragment? = null
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

        App.cometChat.subscribe(true, object : SubscribeCallbacks {
            override fun gotOnlineList(jsonObject: JSONObject) {
                var jsonObject = jsonObject
                Logger.error(TAG, "deleteBuddy : " + jsonObject)
                if (jsonObject.has("message")) {
                    jsonObject = JSONObject()
                }

                Contact.updateAllContacts(jsonObject)
            }

            override fun gotBotList(jsonObject: JSONObject) {
                //                Logger.error(TAG,"gotBotList = "+jsonObject);
                //                Bot.updateAllBots(jsonObject);
            }

            override fun gotRecentChatsList(jsonObject: JSONObject) {
                Logger.error(TAG, "gotRecentChatsList  :  " + jsonObject.toString())
                CCMessageHelper.processRecentChatList(jsonObject)
            }

            override fun onError(jsonObject: JSONObject) {
                Logger.error(TAG, "onError = " + jsonObject)
            }

            override fun onMessageReceived(jsonObject: JSONObject) {
                Logger.error(TAG, "on Message Receive = " + jsonObject)
                if (PreferenceHelper.contains(PreferenceKeys.UserKeys.SINGLE_CHAT_FIREBASE_CHANNEL)!!) {
                    if (!PreferenceHelper.contains("SUBSCRIBED")) {
                        FirebaseMessaging.getInstance().subscribeToTopic(PreferenceHelper.get(PreferenceKeys.UserKeys.SINGLE_CHAT_FIREBASE_CHANNEL))
                    } else {
                        PreferenceHelper.save("SUBSCRIBED", 1)
                    }
                }
                try {
                    if (jsonObject.has("count")) {
                        val jsonArray = jsonObject.getJSONArray("Messages")
                        for (i in 0 until jsonArray.length()) {
                            CCMessageHelper.processOneOnOneMessage(jsonArray.getJSONObject(i))
                        }
                    } else {
                        CCMessageHelper.processOneOnOneMessage(jsonObject)
                    }
                    val messageIntent = Intent(BroadCastReceiverKeys.MESSAGE_DATA_UPDATED_BROADCAST)
                    messageIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.NEW_MESSAGE, 1)
                    PreferenceHelper.getContext().sendBroadcast(messageIntent)

                    val iintent = Intent(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST)
                    iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_RECENT_LIST_KEY, 1)
                    PreferenceHelper.getContext().sendBroadcast(iintent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun gotProfileInfo(jsonObject: JSONObject?) {
                Logger.error(TAG, "gotProfileInfo = " + jsonObject!!)
                if (null != jsonObject && CommonUtils.isJSONValid(jsonObject.toString())) {
                    val data = SessionData.getInstance()
                    data.update(jsonObject)
                }
            }

            override fun gotAnnouncement(jsonObject: JSONObject) {
                Logger.error(TAG, "gotAnnouncement = " + jsonObject)
            }

            override fun onAVChatMessageReceived(jsonObject: JSONObject) {
                Logger.error(TAG, "onAVChatMessageReceived = " + jsonObject)
                CCMessageHelper.processOneOnOneMessage(jsonObject)

                val messageIntent = Intent(BroadCastReceiverKeys.MESSAGE_DATA_UPDATED_BROADCAST)
                messageIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.NEW_MESSAGE, 1)
                PreferenceHelper.getContext().sendBroadcast(messageIntent)

                val iintent = Intent(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST)
                iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_RECENT_LIST_KEY, 1)
                PreferenceHelper.getContext().sendBroadcast(iintent)
            }

            override fun onActionMessageReceived(jsonObject: JSONObject) {
                Logger.error(TAG, "onActionMessageReceived = " + jsonObject)
                try {
                    val action = jsonObject.getString("action")
                    val fromid = jsonObject.getString("from")

                    if (fromid != null && action == "typing_start") {
                        val isTypingIntent = Intent(BroadCastReceiverKeys.MESSAGE_DATA_UPDATED_BROADCAST)
                        isTypingIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.IS_TYPING, 1)
                        isTypingIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.CONTACT_ID, fromid)
                        PreferenceHelper.getContext().sendBroadcast(isTypingIntent)

                        val iintent = Intent(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST)
                        iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_RECENT_LIST_KEY, 1)
                        iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.IS_TYPING, 1)
                        iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.CONTACT_ID, fromid)
                        PreferenceHelper.getContext().sendBroadcast(iintent)
                    } else if (fromid != null && action == "typing_stop") {
                        val isTypingIntent = Intent(BroadCastReceiverKeys.MESSAGE_DATA_UPDATED_BROADCAST)
                        isTypingIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.STOP_TYPING, 1)
                        isTypingIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.CONTACT_ID, fromid)
                        PreferenceHelper.getContext().sendBroadcast(isTypingIntent)

                        val iintent = Intent(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST)
                        iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_RECENT_LIST_KEY, 1)
                        iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.STOP_TYPING, 1)
                        iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.CONTACT_ID, fromid)
                        PreferenceHelper.getContext().sendBroadcast(iintent)
                    } else if (action == "message_deliverd") {
                        val msgId = jsonObject.getString("message_id")
                        val msg = OneOnOneMessage.findByRemoteId(msgId)
                        if (msg != null && msg!!.self === 1) {
                            if (msg!!.messagetick !== CometChatKeys.MessageTypeKeys.MESSAGE_READ) {
                                msg!!.messagetick = CometChatKeys.MessageTypeKeys.MESSAGE_DELIVERD
                                msg!!.save()

                                val iintent = Intent(BroadCastReceiverKeys.MESSAGE_DATA_UPDATED_BROADCAST)
                                iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.NEW_MESSAGE, 1)
                                iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.DELIVERED_MESSAGE, 1)
                                PreferenceHelper.getContext().sendBroadcast(iintent)
                            }
                        } else {
                            App.cometChat.savePendingDeliveredMessages(msgId)
                        }

                    } else if (action == "message_read") {
                        val msgId = jsonObject.getString("message_id")
                        val msg = OneOnOneMessage.findById(msgId)

                        Logger.error(TAG, "msg = " + msg!!)
                        if (msg != null && msg!!.self === 1) {

                            msg!!.messagetick = CometChatKeys.MessageTypeKeys.MESSAGE_READ
                            msg!!.save()

                            val iintent = Intent(BroadCastReceiverKeys.MESSAGE_DATA_UPDATED_BROADCAST)
                            iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.NEW_MESSAGE, 1)
                            iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.READ_MESSAGE, 1)
                            PreferenceHelper.getContext().sendBroadcast(iintent)
                        } else {
                            Timer().schedule(object : TimerTask() {
                                override fun run() {
                                    val msg = OneOnOneMessage.findByRemoteId(msgId)
                                    if (msg != null && msg!!.self === 1) {
                                        if (msg != null && msg!!.self === 1) {
                                            msg!!.messagetick = CometChatKeys.MessageTypeKeys.MESSAGE_READ
                                            msg!!.save()

                                            val iintent = Intent(BroadCastReceiverKeys.MESSAGE_DATA_UPDATED_BROADCAST)
                                            iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.NEW_MESSAGE, 1)
                                            iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.READ_MESSAGE, 1)
                                            PreferenceHelper.getContext().sendBroadcast(iintent)
                                        }
                                    }
                                }
                            }, 3000)
                        }
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onGroupMessageReceived(jsonObject: JSONObject) {
                Logger.error(TAG, "onGroupMessageReceived = " + jsonObject)
                try {
                    if (jsonObject.has("count")) {
                        val jsonArray = jsonObject.getJSONArray("Messages")
                        Logger.error(TAG, "Grp message JsonArray = " + jsonArray)
                        Logger.error(TAG, "Grp message JsonArray length = " + jsonArray.length())
                        for (i in 0 until jsonArray.length()) {
                            Logger.error(TAG, "Grp message process called for " + i)
                            CCMessageHelper.processGroupMessage(jsonArray.getJSONObject(i))
                        }
                    } else {
                        CCMessageHelper.processGroupMessage(jsonObject)
                    }

                    val messageBroadCast = Intent(BroadCastReceiverKeys.GROUP_MESSAGE_DATA_UPDATED_BROADCAST)
                    messageBroadCast.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.NEW_MESSAGE, 1)
                    PreferenceHelper.getContext().sendBroadcast(messageBroadCast)

                    val iintent = Intent(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST)
                    iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_RECENT_LIST_KEY, 1)
                    PreferenceHelper.getContext().sendBroadcast(iintent)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onGroupsError(jsonObject: JSONObject) {
                Logger.error(TAG, "onGroupsError = " + jsonObject)
            }

            override fun onLeaveGroup(jsonObject: JSONObject) {
                Logger.error(TAG, "onLeaveGroup = " + jsonObject)
            }

            override fun gotGroupList(groupList: JSONObject) {
                Logger.error(TAG, "gotGroupList = " + groupList)
                Groups.updateAllGroups(groupList)
            }

            override fun gotGroupMembers(jsonObject: JSONObject) {
                Logger.error(TAG, "gotGroupMembers = " + jsonObject)
            }

            override fun onGroupAVChatMessageReceived(jsonObject: JSONObject) {
                Logger.error(TAG, "onChatroomAVChatMessageReceived = " + jsonObject)
                CCMessageHelper.processGroupMessage(jsonObject)

                val messageBroadCast = Intent(BroadCastReceiverKeys.GROUP_MESSAGE_DATA_UPDATED_BROADCAST)
                messageBroadCast.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.NEW_MESSAGE, 1)
                PreferenceHelper.getContext().sendBroadcast(messageBroadCast)

                val iintent = Intent(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST)
                iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_RECENT_LIST_KEY, 1)
                PreferenceHelper.getContext().sendBroadcast(iintent)
            }

            override fun onGroupActionMessageReceived(jsonObject: JSONObject) {
                Logger.error(TAG, "onChatroomActionMessageReceived = " + jsonObject)
                try {
                    val action_type = jsonObject.getString("action_type")
                    val chatRoomId = jsonObject.getString("chatroom_id")
                    if (action_type == "10") {
                        val finishGroupChatIntent = Intent(BroadCastReceiverKeys.FINISH_GROUP_ACTIVITY)
                        finishGroupChatIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.GROUP_ID, chatRoomId)
                        finishGroupChatIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.KICKED, BroadCastReceiverKeys.IntentExtrasKeys.KICKED)
                        finishGroupChatIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_GROUP_LIST_KEY, 1)
                        PreferenceHelper.getContext().sendBroadcast(finishGroupChatIntent)
                    } else if (action_type == "11") {
                        val finishGroupChatIntent = Intent(BroadCastReceiverKeys.FINISH_GROUP_ACTIVITY)
                        finishGroupChatIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.GROUP_ID, chatRoomId)
                        finishGroupChatIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.BANNED, BroadCastReceiverKeys.IntentExtrasKeys.BANNED)
                        finishGroupChatIntent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_GROUP_LIST_KEY, 1)
                        PreferenceHelper.getContext().sendBroadcast(finishGroupChatIntent)
                    } else if (action_type == "14") {
                        Groups.insertNewGroup(jsonObject.getJSONObject("group"))
                        if (jsonObject.getJSONObject("group").has("push_channel")) {
                            Logger.error(TAG, "onGroupActionMessageReceived: push_channel: " + jsonObject.getJSONObject("group").getString("push_channel"))
                            FirebaseMessaging.getInstance().subscribeToTopic(jsonObject.getJSONObject("group").getString("push_channel"))
                        }
                        val iintent = Intent(BroadCastReceiverKeys.LIST_DATA_UPDATED_BROADCAST)
                        iintent.putExtra(BroadCastReceiverKeys.IntentExtrasKeys.REFRESH_CONTACT_LIST_KEY, 1)
                        PreferenceHelper.getContext().sendBroadcast(iintent)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onLogout() {
                Logger.error(TAG, "onLogout")
            }
        })
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
                R.id.menu1 -> toGroupsChat()
                R.id.menu2 -> showContent = "点击 Item菜单2"
                R.id.menu3 -> toGroupChat()
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

    private fun toGroupsChat() {
        var intent=Intent(this,GroupsActivity::class.java)
//        intent.putParcelableArrayListExtra("users",qbuser)

        startActivity(intent)
    }

    private fun toGroupChat() {
//        var qbuser = mContatsFragment?.getQbuser()


        var intent=Intent(this,CreatGroupsActivity::class.java)
//        intent.putParcelableArrayListExtra("users",qbuser)

        startActivity(intent)
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
                    mContatsFragment = ContactFragment.newInstance(mLogindata)
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
