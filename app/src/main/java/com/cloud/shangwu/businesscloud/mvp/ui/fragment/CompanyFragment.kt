package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.content.Intent
import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.LablesActivity

import com.cloud.shangwu.businesscloud.utils.JumpUtil
import kotlinx.android.synthetic.main.fragment_company.*
import android.text.Spanned
import android.text.style.ImageSpan

import android.text.SpannableString

import android.util.Log
import android.widget.Toast
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.mvp.contract.MineContract
import com.cloud.shangwu.businesscloud.mvp.presenter.MinePresenter
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.IntCompanyActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainCompanyActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.RecommendActivity
import com.cloud.shangwu.businesscloud.utils.FileUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import id.zelory.compressor.Compressor

import java.io.File


class CompanyFragment : BaseFragment() , View.OnClickListener , MineContract.View{
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(errorMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getArea(tx: String) {

    }

    override fun JsonDateOk(json: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun JsonDateErr() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mAlbumFiles: ArrayList<AlbumFile>? = null
    override fun lazyLoad() {

    }

    /**
     * Presenter
     */
    private val mPresenter: MinePresenter by lazy {
        MinePresenter()
    }


    companion object {
        fun getInstance(): CompanyFragment = CompanyFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_company

    override fun initView() {
        mPresenter.attachView(this)
        ll_user_homepage.setOnClickListener(this)
        ll_invite_contact.setOnClickListener(this)
        ll_cooperation_mycom.setOnClickListener(this)
        ll_follow_mycom.setOnClickListener(this)
        ll_bankcard_mycom.setOnClickListener(this)
        ll_setting_mycom.setOnClickListener(this)
        icon.setOnClickListener(this)

        name.setText("阿里巴巴网络技术有限公司")
        name.post(Runnable {
            //获取第一行的宽度
            val lineWidth = name.getLayout().getLineWidth(0)
            //获取第一行最后一个字符的下标
            val lineEnd = name.getLayout().getLineEnd(0)
            //计算每个字符占的宽度
            val widthPerChar = lineWidth / (lineEnd + 1)
            //计算TextView一行能够放下多少个字符
            val numberPerLine = Math.floor((name.getWidth() / widthPerChar).toDouble()).toInt()
            //在原始字符串中插入一个空格，插入的位置为numberPerLine - 1
            val stringBuilder = StringBuilder("阿里巴巴网络技术有限公司").insert(numberPerLine - 1, " ")

            //SpannableString的构建
            val spannableString = SpannableString(stringBuilder.toString() + " ")
            val drawable = resources.getDrawable(R.drawable.v1_mycom)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
            spannableString.setSpan(imageSpan, spannableString.length - 1, spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            name.setText(spannableString)
        })

    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.ll_user_homepage ->{
                Intent(activity, MainCompanyActivity::class.java).apply {
                    startActivity(this)
//                    startActivity(this)
                }
            }

            R.id.ll_invite_contact ->{
                Intent(activity, IntCompanyActivity::class.java).apply {
                    startActivity(this)
//                    startActivity(this)
                }

            }

            R.id.ll_cooperation_mycom ->{

            }

            R.id.ll_follow_mycom ->{
                JumpUtil.Next(activity!!, LablesActivity::class.java)
            }

            R.id.ll_bankcard_mycom ->{
                JumpUtil.Next(activity!!, RecommendActivity::class.java)
            }

            R.id.ll_setting_mycom ->{

            }
            R.id.icon ->{
                Album.image(this)
                        .multipleChoice()
                        .camera(true)
                        .columnCount(4)
                        .selectCount(1)
                        .checkedList(mAlbumFiles)
                        .widget(
                                Widget.newDarkBuilder(activity)
                                        .title("相册")
                                        .build()
                        )
                        .onResult { result ->

                            mAlbumFiles = result
                            Log.i("mAlbumFiles", mAlbumFiles!![0].path)
                            val fileByPath = FileUtils.getFileByPath(mAlbumFiles!![0].path)

                            fileByPath?.apply {
                                getPath(fileByPath)

                            }

                        }
                        .onCancel { Toast.makeText(activity, R.string.canceled, Toast.LENGTH_LONG).show() }
                        .start()

            }
        }
    }
    private fun getPath(photos: File) {
        Log.i("图片大小", "原+${photos.length()}")
        Compressor(activity)
                .compressToFileAsFlowable(photos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ file ->
                    file?.let {
                        App.instance
                        getPath(file)
                        mPresenter.upload(it)
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                    showError(throwable.message!!)
                })

    }
}