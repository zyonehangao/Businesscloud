package com.cloud.shangwu.businesscloud.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.cloud.shangwu.businesscloud.im.models.SampleConfigs
import com.cloud.shangwu.businesscloud.im.utils.Consts
import com.cloud.shangwu.businesscloud.im.utils.QBResRequestExecutor
import com.cloud.shangwu.businesscloud.im.utils.configs.ConfigUtils
import com.cloud.shangwu.businesscloud.utils.DisplayManager
import com.cloud.shangwu.businesscloud.utils.SettingUtil
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import java.util.*
import kotlin.properties.Delegates
import com.cloud.shangwu.businesscloud.mvp.model.db.SQLHelper
import com.cloud.shangwu.businesscloud.widget.helper.MediaLoader
import com.quickblox.sample.core.CoreApp
import com.quickblox.sample.core.utils.ActivityLifecycle
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.io.IOException


/**
 * Created by chengxiaofen on 2018/4/21.
 */
class App : CoreApp() {

    private var refWatcher: RefWatcher? = null


    private var sqlHelper: SQLHelper? = null

    private var sampleConfigs: SampleConfigs? = null

    private var qbResRequestExecutor: QBResRequestExecutor? = null

    companion object {
        private val TAG = "App"

        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application

        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as App
            return app.refWatcher
        }

    }



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

        initSampleConfigs()
    }

    private fun initSampleConfigs() {
        try {
            sampleConfigs = ConfigUtils.getSampleConfigs(Consts.SAMPLE_CONFIG_FILE_NAME)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getSampleConfigs(): SampleConfigs? {
        return this.sampleConfigs
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

    fun clearAppCache() {}

    @Synchronized
     fun getQbResRequestExecutor(): QBResRequestExecutor? {
        this.qbResRequestExecutor= if (this.qbResRequestExecutor == null)
            QBResRequestExecutor() else this.qbResRequestExecutor
        return qbResRequestExecutor
    }


//    fun getQbResRequestExecutor(): QBResRequestExecutor {
//        return if (qbResRequestExecutor == null)
//            qbResRequestExecutor = QBResRequestExecutor()
//        else
//            qbResRequestExecutor
//    }
}