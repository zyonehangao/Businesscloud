package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.R.string.area
import com.cloud.shangwu.businesscloud.base.BaseModel
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by chenxz on 2018/6/3.
 */
class RegisterCompanyModel : BaseModel() {

    fun register(username: String, password: String, repassword: String,area: String, pid: Int, type: Int,email: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.registerCompany(username, password, repassword,area,pid,type,email)
                .compose(SchedulerUtils.ioToMain())
    }

}