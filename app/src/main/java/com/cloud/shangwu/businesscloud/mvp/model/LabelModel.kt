package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.R.attr.type
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

    fun label(countryId  :Int,type: Int,content :String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.label(countryId,type,content).compose(SchedulerUtils.ioToMain())
    }

}