package com.cloud.shangwu.businesscloud.mvp.rx

import com.cloud.shangwu.businesscloud.rx.scheduler.IoMainScheduler


/**
 * Created by chenxz on 2018/4/21.
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }

}