package com.cloud.shangwu.businesscloud.api

import com.cloud.shangwu.businesscloud.mvp.model.bean.HttpResult
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    // git 测试
    /**
     * 登录
     * @param username
     * @param password
     */
    @POST("/pass/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<HttpResult<LoginData>>


    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @param area
     * @param email
     */
    @POST("/pass/save")
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                           @Field("type") type: String,
                           @Field("telephone") telephone: String,
                           @Field("password") password: String,
                           @Field("area") area: String,
                           @Field("email") email: String,
                           @Field("position") position: String,
                           @Field("portrait") portrait: String,
                           @Field("personalCode") personalCode: String,
                           @Field("label") label: String,
                           @Field("invitedCode") invitedCode: String,
                           @Field("intro") intro: String,
                           @Field("impact") impact: String,
                           @Field("hobbys") hobbys: String,
                           @Field("goal") goal: String,
                           @Field("companyName") companyName: String,
                           @Field("clan") clan: String,
                           @Field("businessScope") businessScope: String
    ): Observable<HttpResult<LoginData>>


    /**
     * 企业注册
     * @param username
     * @param password
     * @param repassword
     * @param area
     * @param email
     */
    @POST("/pass/save")
    @FormUrlEncoded
    fun registerCompany(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("area") area: String,
                 @Field("pid") pid: Int,
                 @Field("type") type: Int,
                 @Field("email") email: String
    ): Observable<HttpResult<LoginData>>


    /**
     *发送邮件
     * @param username
     * @param password
     *
     */
    @GET("/pass/email")
    @FormUrlEncoded
    fun email(@Field("email") username: String
                 ): Observable<HttpResult<LoginData>>


    /**
     *获取短信码
     * @param username
     * @param password
     *
     */
    @GET("/pass/sms")
    @FormUrlEncoded
    fun sms(@Field("phone") phone: String
                 ): Observable<HttpResult<LoginData>>

    /**
     *获取短信码
     * @param username
     * @param password
     *
     */
    @GET("/pass/update/password")
    @FormUrlEncoded
    fun password(
            @Field("userName") userName: String,
            @Field("oldPassword") oldPassword: String,
            @Field("newPassword") newPassword: String
                 ): Observable<HttpResult<LoginData>>


}