package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.event.MessageEvent
import com.cloud.shangwu.businesscloud.mvp.ui.activity.message.ToDoListActivity
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.MessageAdapter
import kotlinx.android.synthetic.main.fragment_message.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.ArrayList

class MessageFragment : BaseFragment() {
    var Liist: MutableList<String> = ArrayList()
    var messageAdapter: MessageAdapter? = null

    companion object {
        fun getInstance(): MessageFragment = MessageFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_message
    //是否EventBus 传数据毕传
    override fun useEventBus(): Boolean = true

    override fun initView() {

    }

    override fun lazyLoad() {
        for (i in 0..4) {
            Liist.add("用户A")
        }
        messageAdapter = MessageAdapter(Liist)
        val inflate = View.inflate(activity, R.layout.message_heard, null)
        val ll_todo = inflate.findViewById<LinearLayout>(R.id.ll_todo)
        ll_todo.setOnClickListener {
           //待处理事项
           var  intent = Intent()
            intent.setClass(activity,ToDoListActivity::class.java)
            startActivity(intent)
        }
        recycler_view?.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = messageAdapter
            messageAdapter?.run {
                addHeaderView(inflate)
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onMessageEvent(event: MessageEvent) {
        Toast.makeText(activity, event.message, Toast.LENGTH_SHORT).show()

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEventMainThread(messageEvent: MessageEvent) {
        Log.e("MainThread", Thread.currentThread().name)
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onMessageEventPostThread(messageEvent: MessageEvent) {
        Log.e("PostThread", Thread.currentThread().name)
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessageEventBackgroundThread(messageEvent: MessageEvent) {
        Log.e("BackgroundThread", Thread.currentThread().name)
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onMessageEventAsync(messageEvent: MessageEvent) {
        Log.e("Async", Thread.currentThread().name)
    }
}


