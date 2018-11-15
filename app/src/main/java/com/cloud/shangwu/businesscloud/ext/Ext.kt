package com.cloud.shangwu.businesscloud.ext

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.TextView
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.widget.CustomToast

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by chengxiaofen on 2018/4/22.
 */
/**
 * Log
 */
fun loge(content: String?) {
    loge("CXZ", content)
}

fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: tag)
}

fun Fragment.showToast(content: String) {
    CustomToast(this.activity?.applicationContext, content).show()
}

fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}

fun Activity.showSnackMsg(msg: String) {
    val snackbar = Snackbar.make(this.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.show()
}

fun Fragment.showSnackMsg(msg: String) {
    this.activity ?: return
    val snackbar = Snackbar.make(this.activity!!.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this.activity!!, R.color.white))
    snackbar.show()
}

/**
 * 格式化当前日期
 */
fun formatCurrentDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date())
}
