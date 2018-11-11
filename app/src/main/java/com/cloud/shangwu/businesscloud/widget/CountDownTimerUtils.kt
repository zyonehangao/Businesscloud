package com.haotian.shoubei.shoubeicashiapp.utils

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.R.attr.type


/**
 * Created by chengxiaofen on 2017/11/10.
 */
class CountDownTimerUtils : CountDownTimer {
    private var mTextView: TextView? = null
    private var context: Context? = null
    private var type:String?=null
    /**
     * //     * @param registerActivity
     * @param millisInFuture    The number of millis in the future from the call
     * to [.start] until the countdown is done and [.onFinish]
     * is called.
     * @param countDownInterval The interval along the way to receive
     * [.onTick] callbacks.
     */
    constructor(context: Context, textview: TextView, millisInFuture: Long, countDownInterval: Long) : super(millisInFuture, countDownInterval) {
        this.mTextView = textview
        this.context = context
    }

    constructor(context: Context, textview: TextView, millisInFuture: Long, countDownInterval: Long, type: String) : super(millisInFuture, countDownInterval) {
        this.mTextView = textview
        this.context = context
        this.type = type
    }

    override fun onTick(millisUntilFinished: Long) {
        mTextView!!.isClickable = false //设置不可点击
        mTextView!!.text = "验证码" + millisUntilFinished / 1000 + "s" //设置倒计时时间
        mTextView!!.setTextColor(context!!.resources.getColor(R.color.colorAccent))
        //        R.drawable.shape_verify_btn_press
        mTextView!!.setBackgroundResource(R.color.white) //设置按钮为灰色，这时是不能点击的

        val spannableString = SpannableString(mTextView!!.text.toString()) //获取按钮上的文字
        val span = ForegroundColorSpan(Color.parseColor("#666666"))
        /**
         * public void setSpan(Object what, int start, int end, int flags) {
         * 主要是start跟end，start是起始位置,无论中英文，都算一个。
         * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
         */

        spannableString.setSpan(span, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)//将倒计时的时间设置为红色
        mTextView!!.text = spannableString
    }

    override fun onFinish() {
        if (TextUtils.isEmpty(type)) {
            mTextView!!.text = "重新获取验证码"
            mTextView!!.textSize = 13f
            mTextView!!.setTextColor(context!!.resources.getColor(R.color.Grey500))
            mTextView!!.isClickable = true//重新获得点击
        } else {
            mTextView!!.text = "重新获取验证码"
            mTextView!!.textSize = 13f
            mTextView!!.setTextColor(context!!.resources.getColor(R.color.Grey500))
            mTextView!!.isClickable = true//重新获得点击
        }

    }

}
