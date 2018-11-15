package com.cloud.shangwu.businesscloud.mvp.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData
import kotlin.collections.ArrayList

//class  ChooseHobbiesAdapter (list: ArrayList<ChooseHobbiseData.DataBean.ChildrenBeanX>):BaseQuickAdapter<ChooseHobbiseData.DataBean.ChildrenBeanX,BaseViewHolder>(R.layout.item_heard_view,list){
//    override fun convert(helper: BaseViewHolder?, item: ChooseHobbiseData.DataBean.ChildrenBeanX?) {
//
//    }
//}
//class ChooseHobbiesAdapter(list: ArrayList<ChooseHobbiseData.DataBean.ChildrenBeanX>):BaseMultiItemQuickAdapter<ChooseHobbiseData.DataBean.ChildrenBeanX,BaseViewHolder>(list){
//    override fun convert(helper: BaseViewHolder?, item: ChooseHobbiseData.DataBean.ChildrenBeanX?) {
//
//    }
//
//}

//        (data: List<ChooseHobbiseData.DataBean.ChildrenBeanX>) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data)
//    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
//
//    }

//    var list: ArrayList<ChooseHobbiseData.DataBean.ChildrenBeanX>? = null
//    var stuents: ArrayList<List<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean>>? = null
//    var customExpandableListView: ExpandableListView? = null
//    var adapter: CostomExppandableAdapter? = null

//    init {
//        list = ArrayList()
//        stuents = ArrayList()
//        list!!.addAll(listdata)
//        addItemType(ChooseHobbiseData.DataBean.ChildrenBeanX.Heard, R.layout.item_heard_view)
//        addItemType(ChooseHobbiseData.DataBean.ChildrenBeanX.Text, R.layout.item_text_view)

//    }

//    override fun convert(helper: BaseViewHolder, item: ChooseHobbiseData.DataBean.ChildrenBeanX) {
//
//
//        if (helper.itemViewType == ChooseHobbiseData.DataBean.ChildrenBeanX.Heard) {
//            helper.setText(R.id.tv_type, item.content)
//        }
//        else {
//            customExpandableListView = helper.getView(R.id.expan_ListView)
//            val ivGoToChildClickListener = View.OnClickListener { v ->
//                //获取被点击图标所在的group的索引
//                val map = v.tag as Map<String, Any>
//                val groupPosition = map["groupPosition"] as Int
//                //                boolean isExpand = (boolean) map.get("isExpanded");   //这种是通过tag传值
//                val isExpand = helper.getView<CustomExpandableListView>(R.id.expan_ListView).isGroupExpanded(groupPosition)    //判断分组是否展开
//
//                if (isExpand) {
//                    helper.getView<CustomExpandableListView>(R.id.expan_ListView).collapseGroup(groupPosition)
//                } else {
//                    helper.getView<CustomExpandableListView>(R.id.expan_ListView).expandGroup(groupPosition)
//                }
//            }
//            stuents!!.add(item.children!!)
//            adapter = CostomExppandableAdapter(list!!, stuents!!, mContext, ivGoToChildClickListener)
//
//            customExpandableListView?.run {
//                setGroupIndicator(null)
//                adapter = adapter
//            }
//            helper.setText(R.id.type_name, item.content)
//        }
//    }
//}

