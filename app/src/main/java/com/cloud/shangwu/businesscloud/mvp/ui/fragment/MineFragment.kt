package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.app.App
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.constant.Constant
import com.cloud.shangwu.businesscloud.event.LoginEvent
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.MineContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.MinePresenter
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.ChooseHobbiesActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.LablesActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.SettingActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.mine.UserPresentActivity
import com.cloud.shangwu.businesscloud.utils.FileUtils
import com.cloud.shangwu.businesscloud.utils.ImageLoader
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.mine_toobar.*
import kotlinx.android.synthetic.main.mine_type_layou.*
import java.io.File
import id.zelory.compressor.Compressor
import org.greenrobot.eventbus.EventBus

class MineFragment :BaseFragment() , MineContract.View, View.OnClickListener {
    var HOBBTY :Int = 1000
    private var mAlbumFiles: ArrayList<AlbumFile>? = null
    var userinfo : LoginData ? =null

    //是否EventBus 传数据毕传
    override fun useEventBus(): Boolean = true

    override fun getArea(tx:String) {
        if (tx.isEmpty()) tv_area.text="请选择地区" else tv_area.text=tx
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showData() {

    }


    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    /**
     * Presenter
     */
    private val mPresenter: MinePresenter by lazy {
        MinePresenter()
    }



    companion object {
        fun getInstance(bundle: Bundle): MineFragment {
            var fragment = MineFragment()
            fragment.arguments = bundle
            return fragment
        }


    }

    override fun attachLayoutRes(): Int = R.layout.fragment_mine_home

    override fun initView() {
        mPresenter.attachView(this)
        ll_user_homepage.setOnClickListener(this)
        ll_invite_contact.setOnClickListener(this)
        ll_user_present.setOnClickListener(this)
        ll_hobby.setOnClickListener(this)
        ll_area.setOnClickListener(this)
        ll_card_manager.setOnClickListener(this)
        iv_icon.setOnClickListener(this)
        iv_setting.setOnClickListener(this)

    }

    override fun lazyLoad() {
        userinfo= arguments!!.getSerializable(Constant.LOGIN_KEY) as LoginData
        tv_area.text=userinfo?.area
        name.text=userinfo?.name

        mPresenter.getJsonData(activity as MainActivity)
    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.ll_user_homepage ->{

            }

            R.id.ll_invite_contact ->{
                JumpUtil.Next(activity!!, LablesActivity::class.java)
            }

            R.id.ll_user_present ->{
                JumpUtil.Next(activity!!, UserPresentActivity::class.java)
            }

            R.id.ll_hobby ->{
//                var bundle = Bundle()
//                bundle.putSerializable(Constant.LOGIN_KEY,arguments!!.getSerializable(Constant.LOGIN_KEY))

                EventBus.getDefault().postSticky(LoginEvent(true,arguments!!.getSerializable(Constant.LOGIN_KEY) as LoginData))
                startActivityForResult(Intent(activity!!,ChooseHobbiesActivity::class.java),HOBBTY)
            }

            R.id.ll_area ->{
                activity?.let { mPresenter.showPickerView(activity as MainActivity) }
            }

            R.id.ll_card_manager ->{

            }
            R.id.iv_setting ->{
                JumpUtil.Next(activity,SettingActivity::class.java)
            }
            R.id.iv_icon ->{
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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data==null){
            return
        }
        if (HOBBTY==requestCode){
            val bundle= data?.getStringExtra(Constant.TODO_DATA)
            tv_hobby.text = bundle
        }
    }

    override fun JsonDateOk(path: String) {
        ImageLoader.load(activity, Constant.BASE_URL +"business/image?image= "+ path, iv_icon)
    }

    override fun JsonDateErr() {
    }

}