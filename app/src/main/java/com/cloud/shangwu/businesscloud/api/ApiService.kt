package com.cloud.shangwu.businesscloud.api

import com.cloud.shangwu.businesscloud.mvp.model.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    // git 测试
    /**
     * 登录
     * @param username
     * @param password
     */
    @POST("/business/pass/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String,
              @Field("invitedCode") invitedCode: String
              ): Observable<HttpResult<LoginData>>


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
     * 个人注册
     * @param username
     * @param password
     * @param repassword
     * @param area
     * @param email
     */
    @POST("/business/pass/save")
    @FormUrlEncoded
    fun userRegister(@Field("username") username: String,
                           @Field("password") password: String,
                           @Field("email") email: String,
                           @Field("area") area: String,
                            @Field("pid") pid: String,
                            @Field("type") type: String,
                           @Field("code") position: String,
                     @Field("clan") clan: String,
                     @Field("name") name: String
    ): Observable<HttpResult<LoginData>>


    /**
     * 企业注册
     * @param username
     * @param password
     * @param repassword
     * @param area
     * @param email
     */
    @POST("/business/pass/save")
    @FormUrlEncoded
    fun registerCompany(@Field("companyName") companyName: String,
                 @Field("password") password: String,
                 @Field("area") area: String,
                 @Field("pid") pid: Int,
                 @Field("type") type: Int,
                 @Field("email") email: String,
                 @Field("position") position: String,
                 @Field("username") username: String
    ): Observable<HttpResult<LoginData>>


    /**
     *发送邮件
     * @param username
     * @param password
     *
     */
    @GET("/business/pass/email")
    fun email(@Query("email") content: String): Observable<HttpResult<LoginData>>


    /**
     *获取短信码
     * @param username
     * @param password
     *
     */
    @GET("/business/pass/sms")

    fun sms(@Field("phone") phone: String
                 ): Observable<HttpResult<LoginData>>

    /**
     *获取短信码
     * @param username
     * @param password
     *
     */
    @GET("/business/pass/update/password")

    fun password(
            @Field("userName") userName: String,
            @Field("oldPassword") oldPassword: String,
            @Field("newPassword") newPassword: String
                 ): Observable<HttpResult<LoginData>>
    /**
     *忘记密码
     * @param username
     * @param password
     *
     */
    @POST("/business/pass/forget/password")
    @FormUrlEncoded
    fun forgetpassword(
            @Field("username") userName: String,
            @Field("password") oldPassword: String,
            @Field("code") newPassword: String
                 ): Observable<HttpResult<LoginData>>

    /**上传图片
     *
     * @param password
     *
     */
    @POST("/business/upload")
    @Multipart
    fun uploadFile(@Part file: MultipartBody.Part): Observable<HttpResult<String>>
//     fun upload(@Part("file") file: RequestBody,
//                @Part image: MultipartBody.Part ): Observable<HttpResult<LoginData>>

    /**上传图片
     *
     * @param password
     *
     */
    @POST("/business/upload")
    @Multipart
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
//    fun upload1(@Body file:RequestBody): Observable<HttpResult<LoginData>>
//     fun upload1(@PartMap  file:MultipartBody.Part ): Observable<HttpResult<LoginData>>
    fun uploadImage(@Part("file") description:RequestBody ,
                    @Part file:MultipartBody.Part ):
            Observable<HttpResult<LoginData>>


    /**
     *获取标签接口
     * @param type
     *
     */

    @GET("/business/country/{countryId}/label/search/{type}")
    fun label(
            @Path("countryId") countryId: Int,
            @Path("type") type: Int,
            @Query("content") content: String
    ): Observable<HttpResult<LoginData>>
    /**
     * 获取爱好
     * @param type
     *
     */
    @GET("/business/hobby/list/all")
    fun chooseAll(): Observable<ChooseHobbiseData>

    /**
     *获取热门标签接口
     * @param type
     *
     */

    @GET("/business/country/{countryId}/label/list/{type}/hot")
    fun labelHot(
            @Path("countryId") countryId: Int,
            @Path("type") type: Int
    ): Observable<BaseResult<LabelHot>>


    /**
     *保存标签
     * @param type
     *
     */

    @POST("/business/country/{countryId}/label/update/{lid}")
    fun saveLabel(
            @Path("countryId") countryId: Int,
            @Path("lid") lid:Int,
            @Query("context") context: String,
            @Query("ishot") ishot:Int,
            @Query("lid") id:Int,
            @Query("state") state:Int,
            @Query("type") type:Int
    ): Observable<HttpResult<LabelHot>>


}