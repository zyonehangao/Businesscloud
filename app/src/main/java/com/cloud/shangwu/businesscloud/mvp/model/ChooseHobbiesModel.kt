package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.base.BaseModel
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiesBen
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import io.reactivex.Observable

class ChooseHobbiesModel :BaseModel(){

    fun chooseAll(): Observable<HttpResult<ChooseHobbiesBen.Children>> {
        return RetrofitHelper.service.chooseAll().compose(SchedulerUtils.ioToMain())
    }
}