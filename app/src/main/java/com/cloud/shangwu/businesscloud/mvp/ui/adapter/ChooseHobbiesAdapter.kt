package com.cloud.shangwu.businesscloud.mvp.ui.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiesBen

class ChooseHobbiesAdapter(data: List<ChooseHobbiesBen.Children>) : BaseMultiItemQuickAdapter<ChooseHobbiesBen.Children, BaseViewHolder>(data) {


    init {
        addItemType(ChooseHobbiesBen.Children.Heard, R.layout.item_heard_view)
        addItemType(ChooseHobbiesBen.Children.Text, R.layout.item_text_view)
    }
    override fun convert(helper: BaseViewHolder?, item: ChooseHobbiesBen.Children) {

        when (helper!!.itemViewType) {
            ChooseHobbiesBen.Children.Heard ->
                helper.setText(R.id.tv_type,item?.content)
            ChooseHobbiesBen.Children.Text ->
                helper.setText(R.id.type_name,item?.content)
                        .setText(R.id.type_size,item?.children.size)
            }
        }
    }

