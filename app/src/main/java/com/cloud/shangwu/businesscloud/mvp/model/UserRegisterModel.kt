package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.R.attr.type
import com.cloud.shangwu.businesscloud.R.id.label
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
            , area: String,
            pid: String,
             type: String,
            code: String,
            clan: String,
            name: String,
            telephone: String
    ): Observable<HttpResult<LoginData>>{
        return RetrofitHelper.service.userRegister(
                usernme, password, email
                , area, pid,type,code,clan,name,telephone).compose(SchedulerUtils.ioToMain())
    }

}