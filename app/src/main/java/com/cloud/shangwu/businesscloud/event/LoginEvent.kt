package com.cloud.shangwu.businesscloud.event

import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData

/**
 * Created by chengxiaofen on 2018/6/3.
 */
class LoginEvent(var isLogin: Boolean,var data: LoginData)