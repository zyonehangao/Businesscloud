package com.cloud.shangwu.businesscloud.mvp.ui.activity.login



import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View

import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.im.ui.activity.ChatActivity
import com.cloud.shangwu.businesscloud.im.ui.adapter.CheckboxUsersAdapter
import com.cloud.shangwu.businesscloud.im.utils.chat.ChatHelper
import com.cloud.shangwu.businesscloud.im.utils.qb.QbDialogHolder
import com.cloud.shangwu.businesscloud.mvp.model.bean.Contact
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ChooseContactAdapter
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ContactAdapter

import com.cloud.shangwu.businesscloud.widget.DividerItemDecoration
import com.cloud.shangwu.businesscloud.widget.LetterView
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import kotlinx.android.synthetic.main.activity_creatgroup.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.ArrayList


class CreatGroupActivity : BaseActivity() {

    private var contactNames: Array<String>? = null
    private var layoutManager: LinearLayoutManager? = null
    private var mAdapter: ChooseContactAdapter? = null

    //选中后的数据
    private var checkedList: MutableList<QBUser>? = null
    private val isSelectAll: Boolean = false

    private var isProcessingResultInProgress: Boolean = false

    override fun attachLayoutRes(): Int = R.layout.activity_creatgroup

    override fun useEventBus(): Boolean = true

    override fun enableNetworkTip(): Boolean = false

    override fun initData() {
    }

    override fun initView() {

        iv_black.setOnClickListener{
            beginChat()
        }
        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.knowledge_system)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

//        contactNames = arrayOf("张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123", "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清", "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之", "林远图", "灭绝师太", "段誉", "鸠摩智")
//        var data = intent.getParcelableArrayListExtra<Contact>("users")
//        val data = intent.extras.getSerializable("chat")

//        Log.i("test",)
        layoutManager = LinearLayoutManager(this)
//        mAdapter = ChooseContactAdapter(this, data as ArrayList<Contact>?)
//
//
        contact_list.layoutManager=layoutManager
//        contact_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))
//        contact_list.adapter = mAdapter
//
//
//        letter_view!!.setCharacterListener(object : LetterView.CharacterClickListener {
//            override fun clickCharacter(character: String) {
//                layoutManager!!.scrollToPositionWithOffset(mAdapter!!.getScrollPosition(character), 0)
//            }
//            override fun clickArrow() {
//                layoutManager!!.scrollToPositionWithOffset(0, 0)
//            }
//        })

    }

    private fun beginChat() {
        var checkedContact = mAdapter?.checkedContacts
        checkedList= mutableListOf()
        for (a in checkedContact!!) {
            checkedList!!.add(a.mUser)
        }
        ChatHelper.getInstance().createDialogWithSelectedUsers(checkedList, object : QBEntityCallback<QBChatDialog> {
            override fun onSuccess(qbChatDialog: QBChatDialog, bundle: Bundle) {
                Log.d("CREATE-CHAT", "onSuccess")

                val intent = Intent(this@CreatGroupActivity, ChatActivity::class.java)
                intent.putExtra(ChatActivity.EXTRA_DIALOG_ID, qbChatDialog)
                startActivity(intent)

            }

            override fun onError(e: QBResponseException) {

            }
        })
    }

    override fun start() {
        loadUsersFromQb()
    }

    private fun loadUsersFromQb() {
        val tags = ArrayList<String>()
        tags.add("businesscloud")

//
        QBUsers.getUsersByTags(tags, null).performAsync(object : QBEntityCallback<ArrayList<QBUser>> {
            override fun onSuccess(result: ArrayList<QBUser>, params: Bundle) {

                mAdapter = ChooseContactAdapter(this@CreatGroupActivity, result)

                contact_list.layoutManager = layoutManager
                contact_list.addItemDecoration(DividerItemDecoration(this@CreatGroupActivity, DividerItemDecoration.VERTICAL_LIST))
                contact_list.adapter = mAdapter


                letter_view!!.setCharacterListener(object : LetterView.CharacterClickListener {
                    override fun clickCharacter(character: String) {
                        layoutManager!!.scrollToPositionWithOffset(mAdapter!!.getScrollPosition(character), 0)
                    }

                    override fun clickArrow() {
                        layoutManager!!.scrollToPositionWithOffset(0, 0)
                    }
                })
            }

            override fun onError(e: QBResponseException) {

            }
        })
    }


}


