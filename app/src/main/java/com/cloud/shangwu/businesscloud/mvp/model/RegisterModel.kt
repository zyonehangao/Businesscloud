package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import com.cloud.shangwu.businesscloud.base.BaseModel
import io.reactivex.Observable

/**
 * Created by chenxz on 2018/6/3.
 */
class RegisterModel : BaseModel() {

    fun registerWanAndroid(username: String, password: String, repassword: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.registerWanAndroid(username, password, repassword)
                .compose(SchedulerUtils.ioToMain())
    }

}