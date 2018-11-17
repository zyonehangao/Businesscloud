package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.base.BaseActivity
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.mvp.contract.UserRegisterContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.UserRegisterPresenter
import com.cloud.shangwu.businesscloud.utils.FileUtils
import com.cloud.shangwu.businesscloud.utils.ImageLoader
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_user_register.*
import java.util.ArrayList
import java.io.File
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import id.zelory.compressor.Compressor


class UsersRegisterActivity : BaseActivity(), UserRegisterContract.View {
    private var mAlbumFiles: ArrayList<AlbumFile>? = null
    override fun uploadOk(json: String) {

    }

    override fun uploadErr() {

    }

    override fun JsonDateOk(path: String) {
        ImageLoader.load(this@UsersRegisterActivity, Constant.BASE_URL +"business/image?image= "+ path, iv_logo)
    }

    override fun JsonDateErr() {
    }

    private val mPresenter: UserRegisterPresenter by lazy {
        UserRegisterPresenter()
    }

    override fun userRegisterOK(data: LoginData) {

    }

    override fun userRegisterEdrr() {
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }

    private val REQUEST_CODE_CHOOSE = 23


    override fun attachLayoutRes(): Int = R.layout.activity_user_register

    override fun initData() {


        btn_register.setOnClickListener {
            JumpUtil.Next(this@UsersRegisterActivity, LablesActivity::class.java)
        }
        iv_logo.setOnClickListener {

            Album.image(this)
                    .multipleChoice()
                    .camera(true)
                    .columnCount(4)
                    .selectCount(1)
                    .checkedList(mAlbumFiles)
                    .widget(
                            Widget.newDarkBuilder(this)
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
                    .onCancel { Toast.makeText(this@UsersRegisterActivity, R.string.canceled, Toast.LENGTH_LONG).show() }
                    .start()

        }

    }

    private fun getPath(photos: File) {
        Log.i("图片大小", "原+${photos.length()}")
        Compressor(this)
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

    override fun initView() {

        mPresenter.attachView(this)

    }

    override fun start() {
        //爱好
        rl_choose_hobbies.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("1", "10000")
            JumpUtil.Next(this@UsersRegisterActivity,
                    ChooseHobbiesActivity::class.java, bundle)
        }
        //职业
        rl_labels_please.setOnClickListener {
            JumpUtil.Next(this@UsersRegisterActivity, LabelsPleaseActivity::class.java)
        }
    }

}