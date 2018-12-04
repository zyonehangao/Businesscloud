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

    fun register(companyName: String, password: String,area: String, pid: Int, type: Int,email: String,position: String,username: String,telephone: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.registerCompany(companyName, password, area,pid,type,email,position,username,telephone)
                .compose(SchedulerUtils.ioToMain())
    }

}