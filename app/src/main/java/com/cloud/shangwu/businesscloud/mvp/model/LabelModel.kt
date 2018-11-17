package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.base.BaseModel
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by chenxz on 2018/6/3.
 */
class LabelModel : BaseModel() {

    fun label(content :String,countryId  :Int,type: Int): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.label(content,countryId,type).compose(SchedulerUtils.ioToMain())
    }

}