package com.cloud.shangwu.businesscloud.mvp.ui.fragment

import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.R.string.area
import com.cloud.shangwu.businesscloud.base.BaseFragment
import com.cloud.shangwu.businesscloud.ext.showToast
import com.cloud.shangwu.businesscloud.mvp.contract.MineContract
import com.cloud.shangwu.businesscloud.mvp.contract.RegisterPersonalContract
import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData
import com.cloud.shangwu.businesscloud.mvp.presenter.MinePresenter
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPersonalPresenter
import com.cloud.shangwu.businesscloud.mvp.presenter.RegisterPresenter
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.ChooseHobbiesActivity
import com.cloud.shangwu.businesscloud.mvp.ui.activity.login.MainActivity
import com.cloud.shangwu.businesscloud.utils.DialogUtil
import com.cloud.shangwu.businesscloud.utils.JumpUtil
import kotlinx.android.synthetic.main.fragment_mine_home.*
import kotlinx.android.synthetic.main.mine_type_layou.*

class MineFragment :BaseFragment() , MineContract.View, View.OnClickListener {

//    override fun showPicker(tx: String) {
//        area=tx
//        tv_location?.run {
//            text=tx
//            setTextColor(resources.getColor(R.color.Black))
//        }
//    }
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
        fun getInstance(): MineFragment = MineFragment()
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

    }

    override fun lazyLoad() {

        mPresenter.getJsonData(activity as MainActivity)
    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.ll_user_homepage ->{

            }

            R.id.ll_invite_contact ->{

            }

            R.id.ll_user_present ->{

            }

            R.id.ll_hobby ->{
                JumpUtil.Next(activity!!,ChooseHobbiesActivity::class.java)
            }

            R.id.ll_area ->{
                activity?.let { mPresenter.showPickerView(activity as MainActivity) }
//
                if (mPresenter.getArea().isEmpty()) tv_area.text="请选择地区" else tv_area.text=mPresenter.getArea()
            }

            R.id.ll_card_manager ->{

            }
        }
    }
}