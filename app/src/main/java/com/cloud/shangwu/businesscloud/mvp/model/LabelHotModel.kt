package com.cloud.shangwu.businesscloud.mvp.model

import com.cloud.shangwu.businesscloud.base.BaseModel
import com.cloud.shangwu.businesscloud.http.RetrofitHelper
import com.cloud.shangwu.businesscloud.mvp.model.bean.BaseResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LabelHot
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by chenxz on 2018/6/3.
 */
class LabelHotModel : BaseModel() {

    fun label(countryId  :Int,type: Int): Observable<BaseResult<LabelHot>> {
        return RetrofitHelper.service.labelHot(countryId,type).compose(SchedulerUtils.ioToMain())
    }

    fun saveLabel(countryId  :Int,lid:Int,context:String,ishot:Int,id:Int,status:Int,type: Int): Observable<HttpResult<LabelHot>> {
        return RetrofitHelper.service.saveLabel(countryId,lid,context,ishot,id,status,type).compose(SchedulerUtils.ioToMain())
    }

}