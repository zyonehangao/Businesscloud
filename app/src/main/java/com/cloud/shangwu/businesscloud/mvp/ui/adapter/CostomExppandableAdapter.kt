package com.cloud.shangwu.businesscloud.mvp.ui.adapter
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData
import org.w3c.dom.Text


import java.util.HashMap

class CostomExppandableAdapter( val classes: MutableList<ChooseHobbiseData.DataBean.ChildrenBeanX>,  var stuents: MutableList<MutableList<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean>>,  val context: Context,  val ivGoToChildClickListener:View.OnClickListener) : BaseExpandableListAdapter() {
//    , internal var ivGoToChildClickListener: View.OnClickListener

    override fun getGroupCount(): Int {    //组的数量
        return classes.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {    //某组中子项数量
        return stuents[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {     //某组
        return classes[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {  //某子项
        return stuents[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }


    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val groupHold: GroupHold
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_hobbies_group, null)
            groupHold = GroupHold()
            groupHold.tvGroupName = convertView!!.findViewById<View>(R.id.type_name) as TextView
            groupHold.ivGoToChildLv = convertView.findViewById<View>(R.id.type_size) as TextView

            convertView.tag = groupHold

        } else {
            groupHold = convertView.tag as GroupHold

        }

        val groupName = classes[groupPosition].content
        groupHold.tvGroupName!!.text = groupName
        groupHold.ivGoToChildLv?.text="${classes.size}"
        //取消默认的groupIndicator后根据方法中传入的isExpand判断组是否展开并动态自定义指示器
//        if (isExpanded) {   //如果组展开
//            groupHold.ivGoToChildLv!!.setImageResource(R.drawable.down)
//        } else {
//            groupHold.ivGoToChildLv!!.setImageResource(R.drawable.updat)
//        }

        //setTag() 方法接收的类型是object ，所以可将position和converView先封装在Map中。Bundle中无法封装view,所以不用bundle
        val tagMap = HashMap<String, Any>()
        tagMap["groupPosition"] = groupPosition
        tagMap["isExpanded"] = isExpanded
//        groupHold.ivGoToChildLv!!.tag = tagMap

        //图标的点击事件
//        groupHold.ivGoToChildLv!!.setOnClickListener(ivGoToChildClickListener)

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?,
                              parent: ViewGroup): View {
        var convertView = convertView
        val childHold: ChildHold
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_hobbies_child, null)
            childHold = ChildHold()
            childHold.tvChildName = convertView!!.findViewById<View>(R.id.tv_elv_childName) as TextView
            //            childHold.cbElvChild = (CheckBox) convertView.findViewById(R.id.cb_elvChild);
            convertView.tag = childHold
        } else {
            childHold = convertView.tag as ChildHold
        }

        val childName = stuents[groupPosition][childPosition].content
//        val childName = "paobu "
        childHold.tvChildName?.text = childName

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true    //默认返回false,改成true表示组中的子条目可以被点击选中
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    internal inner class GroupHold {
        var tvGroupName: TextView? = null
        var ivGoToChildLv: TextView? = null
    }

    internal inner class ChildHold {
        var tvChildName: TextView? = null
        //        CheckBox cbElvChild;
    }
}

