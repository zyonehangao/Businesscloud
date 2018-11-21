package com.cloud.shangwu.businesscloud.mvp.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.cloud.shangwu.businesscloud.R.id.label
import com.cloud.shangwu.businesscloud.R.string.username
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.base.BasePresenter
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.http.exception.ExceptionHandle
import com.cloud.shangwu.businesscloud.http.function.RetryWithDelay
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterPersonalContract
import com.cloud.shangwu.businesscloud.mvp.model.UserRegisterModel
import com.cloud.shangwu.businesscloud.mvp.model.bean.*
import com.cloud.shangwu.businesscloud.ui.activity.RegisterPersonalActivity
import com.cloud.shangwu.businesscloud.utils.GetJsonDataUtil
import com.google.gson.Gson


class RegisterPersonalPresenter : BasePresenter<RegisterPersonalContract.View>(), RegisterPersonalContract.Persenter {


    private var options1Items = ArrayList<JsonData.DataBean>()
    private val options2Items = ArrayList<ArrayList<JsonData.DataBean.ChildrenBeanX>>()
    private val options3Items = ArrayList<ArrayList<ArrayList<JsonData.DataBean.ChildrenBeanX.ChildrenBean>>>()
    private var thread: Thread? = null
    private val MSG_LOAD_DATA = 0x0001
    private val MSG_LOAD_SUCCESS = 0x0002
    private val MSG_LOAD_FAILED = 0x0003
    private var isLoaded = false
    private val userRegisterModer: UserRegisterModel by lazy {
        UserRegisterModel()
    }

    override fun userRegister(usernme: String, password: String, email: String, area: String, pid: String,type: String, code: String) {
        mView?.showLoading()
        val subscribe = userRegisterModer.UserRegister(usernme, password, email, area, pid,type, code)
                .retryWhen(RetryWithDelay())
                .subscribe({ res ->
                    mView?.apply {
                        if (res.code != Constant.OK) {
                            showError(res.message)
                            registerFail()
                            hideLoading()
                        } else {
                            registerOK(res.data)
                        }
                    }
                }, { t ->
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(subscribe)

    }

    // 弹出选择器
    override fun showPickerView() {

        val pvOptions = OptionsPickerBuilder(mView as BaseActivity, OnOptionsSelectListener { options1, options2, options3, v ->
            //返回的分别是三个级别的选中位置
            var tx = options1Items[options1].pickerViewText+"-"+
                    options2Items[options1][options2].pickerViewText+"-"+
                    options3Items[options1][options2][options3].pickerViewText
            Log.i("级别",tx)
            Toast.makeText(mView as BaseActivity, tx, Toast.LENGTH_SHORT).show()
            if (mView is RegisterPersonalActivity) {
                (mView as RegisterPersonalActivity).showPicker(tx)
            }

        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build<Any>()

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items as List<JsonData.DataBean>, options2Items as List<List<JsonData.DataBean.ChildrenBeanX>> , options3Items as  List<List<List<JsonData.DataBean.ChildrenBeanX.ChildrenBean>>>)//三级选择器
        pvOptions.show()

    }

    fun parseData(result: String): ArrayList<JsonData.DataBean> {//Gson 解析
        val detail =ArrayList<JsonData.DataBean>()
        try {
            val fromJson = Gson().fromJson(result, JsonData::class.java)
            detail.addAll(fromJson.data!!)
        } catch (e: Exception) {
            e.printStackTrace()
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED)
        }
        return detail
    }

    override fun getJsonData() {
//        val Json = GetJsonDataUtil().getJson(mView as BaseActivity, "province.json")//获取assets目录下的json文件数据
        val Json = GetJsonDataUtil().getJson(mView as BaseActivity, "response.json")//获取assets目录下的json文件数据
        var jsonBean = parseData(Json)//用Gson 转成实体
        options1Items = jsonBean
        for (i in jsonBean.indices) {//遍历省份
            val CityList =ArrayList<JsonData.DataBean.ChildrenBeanX>()//该省的城市列表（第二级）
            val Province_AreaList = ArrayList<ArrayList<JsonData.DataBean.ChildrenBeanX.ChildrenBean>>()//该省的所有地区列表（第三极）

            for (c in 0 until jsonBean[i].children!!.size) {//遍历该省份的所有城市
                val CityName = jsonBean[i].children!![c]
                CityList.add(CityName!!)//添加城市
                val City_AreaList = java.util.ArrayList<JsonData.DataBean.ChildrenBeanX.ChildrenBean>()//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean[i].children!![c].children == null || jsonBean[i].children!![c].children!!.size === 0) {
                    City_AreaList.add(JsonData.DataBean.ChildrenBeanX.ChildrenBean())
                } else {
                    val areaList =ArrayList<JsonData.DataBean.ChildrenBeanX.ChildrenBean>()//该省的城市列表（第二级）
                    jsonBean[i].children!![c].children!!.forEachIndexed { index, childrenBean ->
                        areaList.add(childrenBean)
                    }
                    City_AreaList.addAll(areaList)
                }
                Province_AreaList.add(City_AreaList)//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList)

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList)
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS)
    }
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_LOAD_DATA -> if (thread == null) {//如果已创建就不再重新创建子线程了

                    Toast.makeText(mView as BaseActivity, "Begin Parse Data", Toast.LENGTH_SHORT).show()
                    thread = Thread(Runnable {
                        // 子线程中解析省市区数据
                        getJsonData()
                    })
                    thread?.start()
                }

                MSG_LOAD_SUCCESS -> {
                    Toast.makeText(mView as BaseActivity, "数据加载成功", Toast.LENGTH_SHORT).show()
                    isLoaded = true
                }

                MSG_LOAD_FAILED -> Toast.makeText(mView as BaseActivity, "Parse Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun start() {
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS)
    }

}