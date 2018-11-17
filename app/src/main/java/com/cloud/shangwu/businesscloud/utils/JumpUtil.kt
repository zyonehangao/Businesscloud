package com.cloud.shangwu.businesscloud.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.cloud.shangwu.businesscloud.app.App.Companion.context

/**
 * Created by chengxiaofen on 2018/8/21.
 * Acticity界面跳转工具类
 */

object JumpUtil {

    /**
     * 不带参数的跳转
     *
     * @param context
     * @param targetClazz
     */

    fun Next(context: Context, targetClazz: Class<out Activity>) {
        val mIntent = Intent(context, targetClazz)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(mIntent)
        (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    /**
     * 带参数不带动画的跳转
     *
     * @param context
     * @param targetClazz
     * @param bundle
     */
    fun Next(activity: Activity, targetClazz: Class<out Activity>, bundle: Bundle?) {
        val mIntent = Intent(context, targetClazz)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        context.startActivity(mIntent)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }


    /**
     * 带参数,共享元素跳转
     *
     * @param context
     * @param targetClazz
     * @param bundle
     */
    fun Next(context: Context, targetClazz: Class<out Activity>, bundle: Bundle?, options: Bundle) {
        val mIntent = Intent(context, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mIntent, options)
        (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }


    /**
     * @param context
     * @param targetClazz
     * @param bundle
     * @param flags
     */
    fun Next(context: Context, targetClazz: Class<out Activity>, bundle: Bundle?, flags: Int?) {
        val mIntent = Intent(context, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        if (flags != null) {
            mIntent.flags = flags
        }
        context.startActivity(mIntent)
        (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }


    /**
     * @param context
     * @param targetClazz
     * @param requestCode
     * @param bundle
     */
    fun startForResult(context: Activity, targetClazz: Class<out Activity>, requestCode: Int, bundle: Bundle?) {
        val mIntent = Intent(context, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        context.startActivityForResult(mIntent, requestCode)
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }

    /**
     * @param fragment
     * @param targetClazz
     * @param requestCode
     * @param bundle
     */
    fun startForResult(fragment: Fragment, targetClazz: Class<out Activity>, requestCode: Int, bundle: Bundle?) {
        val mIntent = Intent(fragment.activity, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        fragment.startActivityForResult(mIntent, requestCode)

    }

}
