package com.cloud.shangwu.businesscloud.ui.activity

import android.content.ComponentName
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.event.MessageEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.im.Keys.BroadCastReceiverKeys
import com.cloud.shangwu.businesscloud.im.helpers.CCMessageHelper
import com.cloud.shangwu.businesscloud.im.models.Contact
import com.cloud.shangwu.businesscloud.im.models.Conversation
import com.cloud.shangwu.businesscloud.im.models.Groups
import com.cloud.shangwu.businesscloud.im.models.OneOnOneMessage

import com.cloud.shangwu.businesscloud.mvp.contract.LoginContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.LoginPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.ForgetPassword
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity

import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.RegisterActivity

import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import com.google.firebase.messaging.FirebaseMessaging
import com.inscripts.helpers.PreferenceHelper
import com.inscripts.interfaces.Callbacks
import com.inscripts.interfaces.LaunchCallbacks
import com.inscripts.interfaces.SubscribeCallbacks
import com.inscripts.keys.CometChatKeys
import com.inscripts.keys.PreferenceKeys
import com.inscripts.orm.SugarContext
import com.inscripts.utils.CommonUtils
import com.inscripts.utils.Logger
import com.inscripts.utils.SessionData
import cometchat.inscripts.com.cometchatcore.coresdk.CometChat
import cometchat.inscripts.com.cometchatcore.coresdk.MessageSDK
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.white_toolbar.*
import org.greenrobot.eventbus.EventBus

import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins.onError
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class LoginActivity : BaseActivity(), LoginContract.View {
    override fun initData() {

    }

    private var cometChat: CometChat? =null
    private var mdata: LoginData? =null

    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")


    /**
     * token
     */
    private var token: String by Preference(Constant.TOKEN_KEY, "")

    /**
     * token
     */


    private val mPresenter: LoginPresenter by lazy {
        LoginPresenter()
    }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }



    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun useEventBus(): Boolean = true

    override fun enableNetworkTip(): Boolean = false



    override fun initView() {

        tl_title.run {
            title = ""
            toolbar_withe_name.run {
                text = getString(R.string.login)

            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        mPresenter.attachView(this)
        et_username.setText(user)
        btn_login.setOnClickListener(onClickListener)
        tv_sign_up.setOnClickListener(onClickListener)
        tv_forgetpsd.setOnClickListener(onClickListener)
    }

    override fun start() {

    }

    override fun loginSuccess(data: LoginData) {
        showToast(getString(R.string.login_success))
        isLogin = true
        user = data.username
        pwd = et_password.text.toString()
        token = data.token
        mdata=data

        EventBus.getDefault().postSticky(LoginEvent(isLogin, data))

        var bundle = Bundle()
        bundle.putSerializable("login", data)

        JumpUtil.Next(this, MainActivity::class.java, bundle)
        finish()
    }




    override fun loginFail() {
        hideLoading()
    }

    /**
     * OnClickListener
     */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                login()
            }
            R.id.tv_sign_up -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)

            }
            R.id.tv_forgetpsd -> {
                val intent = Intent(this@LoginActivity, ForgetPassword::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * Login
     */
    private fun login() {

        if (validate()) run {

            mPresenter.login(et_username.text.toString(), et_password.text.toString(), invitation_code.text.toString())
            App.cometChat.loginWithUID(this@LoginActivity, et_username.text.toString(), object : Callbacks {
                override fun successCallback(jsonObject: JSONObject) {
                    Log.d("LoginActivity", "Login Success : " + jsonObject.toString())
                    Toast.makeText(this@LoginActivity, jsonObject.toString(), Toast.LENGTH_LONG).show()
//                    launchChat(et_username.text.toString())
                }

                override fun failCallback(jsonObject: JSONObject) {
                    Log.d("LoginActivity", "Login Fail : " + jsonObject.toString())
                    Toast.makeText(this@LoginActivity, jsonObject.toString(), Toast.LENGTH_LONG).show()
                }
            })
        }

    }


    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()

        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid

    }

    override fun onDestroy() {
        mDialog.dismiss()
//        App.cometChat.unsubscribe()
        super.onDestroy()
    }

    /**
     * Launches the chat.
     */
    private fun launchChat( id: String) {


        App.cometChat.subscribe(true, object :SubscribeCallbacks {
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

    }



}
