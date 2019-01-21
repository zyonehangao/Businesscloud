package com.cloud.shangwu.businesscloud.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.cloud.shangwu.businesscloud.mvp.model.db.SQLHelper
import com.cloud.shangwu.businesscloud.utils.DisplayManager
import com.cloud.shangwu.businesscloud.utils.SettingUtil
import com.cloud.shangwu.businesscloud.widget.helper.MediaLoader
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import cometchat.inscripts.com.cometchatcore.coresdk.CometChat
import java.io.IOException
import java.util.*
import kotlin.properties.Delegates
import com.inscripts.interfaces.Callbacks
import android.R.attr.apiKey
import org.json.JSONObject


/**
 * Created by chengxiaofen on 2018/4/21.
 */
class App : Application() {

    private var refWatcher: RefWatcher? = null


    private var sqlHelper: SQLHelper? = null


    private var  licenseKey :String= "COMETCHAT-BQOKW-XKMT0-99PV2-UZKSR"
    private var  apiKey :String = "52411x8eb5e86c6302b88ad1bc0cec76cab8a1"

    companion object {

        private val TAG = "App"
        var context: Context by Delegates.notNull()
            private set


        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Application
        lateinit var cometChat: CometChat

        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as App
            return app.refWatcher
        }

    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        refWatcher = setupLeakCanary()
        DisplayManager.init(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initTheme()
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )
        cometChat = CometChat.getInstance(context)
        cometChat.initializeCometChat("", licenseKey, apiKey, true, object : Callbacks {
            override fun successCallback(jsonObject: JSONObject) {

            }

            override fun failCallback(jsonObject: JSONObject) {

            }
        })
    }


    /**
     * 初始化主题
     */
    private fun initTheme() {

        if (SettingUtil.getIsAutoNightMode()) {
            val nightStartHour = SettingUtil.getNightStartHour().toInt()
            val nightStartMinute = SettingUtil.getNightStartMinute().toInt()
            val dayStartHour = SettingUtil.getDayStartHour().toInt()
            val dayStartMinute = SettingUtil.getDayStartMinute().toInt()

            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val nightValue = nightStartHour * 60 + nightStartMinute
            val dayValue = dayStartHour * 60 + dayStartMinute
            val currentValue = currentHour * 60 + currentMinute

            if (currentValue >= nightValue || currentValue <= dayValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SettingUtil.setIsNightMode(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SettingUtil.setIsNightMode(false)
            }
        } else {
            // 获取当前的主题
            if (SettingUtil.getIsNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }


    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }


    /** 获取数据库Helper  */
    fun getSQLHelper(): SQLHelper {
        if (sqlHelper == null)
            sqlHelper = SQLHelper(instance)
        return sqlHelper as SQLHelper
    }

    /** 摧毁应用进程时候调用  */
    override fun onTerminate() {
        if (sqlHelper != null)
            sqlHelper!!.close()
        super.onTerminate()
    }

}