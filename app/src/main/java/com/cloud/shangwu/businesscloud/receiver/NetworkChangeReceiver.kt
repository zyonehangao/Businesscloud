package com.cloud.shangwu.businesscloud.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cloud.shangwu.businesscloud.event.NetworkChangeEvent
import com.cloud.shangwu.businesscloud.utils.NetWorkUtil
import com.cloud.shangwu.businesscloud.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 * Created by chengxiaofen on 2018/8/1.
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上一次的网络状态
     */
    private var hasNetwork: Boolean by Preference("has_network", true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}