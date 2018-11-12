package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.base.BaseModel
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import io.reactivex.Observable

class UserRegisterModel : BaseModel() {
    fun UserRegister(
            usernme: String
            , password: String
            , email: String
            , invitedCode: String,
            portrait: String,
            hobbys: String,
            label: String
    ): Observable<HttpResult<LoginData>>{
        return RetrofitHelper.service.userRegister(
                usernme, password, email
                , invitedCode, portrait, hobbys, label).compose(SchedulerUtils.ioToMain())
    }

}