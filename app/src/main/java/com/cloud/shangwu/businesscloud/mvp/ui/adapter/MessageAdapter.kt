package com.cloud.shangwu.businesscloud.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cloud.shangwu.businesscloud.R

class MessageAdapter(datas: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_message_list, datas) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_name,item)
    }
}