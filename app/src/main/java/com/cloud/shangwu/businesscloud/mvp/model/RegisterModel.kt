package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.R.attr.type
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import com.cloud.shangwu.businesscloud.base.BaseModel
import io.reactivex.Observable
import retrofit2.http.Field

/**
 * Created by chenxz on 2018/6/3.
 */
class RegisterModel : BaseModel() {

    fun register(username: String,
                 type: String,
                 telephone: String,
                 password: String,
                 area: String,
                 email: String,
                 position: String,
                 portrait: String,
                 personalCode: String,
                 label: String,
                 invitedCode: String,
                 intro: String,
                 impact: String,
                 hobbys: String,
                 goal: String,
                 companyName: String,
                 clan: String,
                 businessScope: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.register(
                username,
                type,
                telephone,
                password,
                area,
                email,
                position,
                portrait,
                personalCode,
                label,
                invitedCode,
                intro,
                impact,
                hobbys,
                goal,
                companyName,
                clan,
                businessScope
        ).compose(SchedulerUtils.ioToMain())
    }

}