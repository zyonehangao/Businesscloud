package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import com.cloud.shangwu.businesscloud.base.BaseModel
import io.reactivex.Observable

/**
 * Created by chengxiaofen on 2018/5/27.
 */
class LoginModel : BaseModel() {

    fun login(username: String, password: String, invitedCode: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.login(username, password,invitedCode)
                .compose(SchedulerUtils.ioToMain())
    }

}