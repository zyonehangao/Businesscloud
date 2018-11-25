package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData;
import com.cloud.shangwu.businesscloud.widget.CustomExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MultiItemAdapter extends BaseMultiItemQuickAdapter<ChooseHobbiseData.DataBean.ChildrenBeanX, BaseViewHolder> {
    private List<ChooseHobbiseData.DataBean.ChildrenBeanX> data;
    private List<ChooseHobbiseData.DataBean.ChildrenBeanX> gruopList;
    private List<List<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean>> stuents;
    private List<List<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean>> hobbiesList;
    private List<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean> childrenlist;

    public MultiItemAdapter(List<ChooseHobbiseData.DataBean.ChildrenBeanX> data) {
        super(data);
        this.data = data;
        stuents = new ArrayList<>();
        hobbiesList = new ArrayList<>();
        gruopList = new ArrayList<>();
        /**
         * addItemType中的type种类，必须和接收到的种类数目一模一样。
         * 种类：有几种type，就要写几个addItemType。少写或者错写，会直接报错！！！
         *  (android.content.res.Resources$NotFoundException: Resource ID *******)
         *  时刻注意！！！！
         *  例如：这个type有10种！。（type=1,2,3...10）你就得写
         *     addItemType(1, R.layout.item_test_one);
         *     addItemType(2, R.layout.item_test_two);
         *     addItemType(3, R.layout.item_test_two);
         *     ....
         *     addItemType(10, R.layout.item_test_two);
         *     漏写一个就会报错！！！血的教训啊！！！！
         */
        addItemType(ChooseHobbiseData.DataBean.ChildrenBeanX.Companion.getHeard(), R.layout.item_heard_view);
        addItemType(ChooseHobbiseData.DataBean.ChildrenBeanX.Companion.getText(), R.layout.item_text_view);

    }


    @Override
    protected void convert(BaseViewHolder helper, ChooseHobbiseData.DataBean.ChildrenBeanX item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setText(R.id.tv_type, item.getContent());
                break;
            case 1:
                CustomExpandableListView expandableListView = ((CustomExpandableListView) helper.getView(R.id.expan_ListView));

                View.OnClickListener ivGoToChildClickListener = v -> {
                    //获取被点击图标所在的group的索引
                    Map<String, Object> map = (Map<String, Object>) v.getTag();
                    int groupPosition = (int) map.get("groupPosition");
//                boolean isExpand = (boolean) map.get("isExpanded");   //这种是通过tag传值
                    //判断分组是否展开
                    boolean isExpand = expandableListView.isGroupExpanded(groupPosition);

                    if (isExpand) {
                        expandableListView.collapseGroup(groupPosition);
                    } else {
                        expandableListView.expandGroup(groupPosition);
                    }
                };
                for (int i = 0; i < data.size(); i++) {
                     if (5==data.get(i).getHigher()){
                        gruopList.add(data.get(i));
                        stuents.add(data.get(i).getChildren());
                    }
                }
                CostomExppandableAdapter costomExppandableAdapter =
                        new CostomExppandableAdapter(gruopList, stuents, mContext, ivGoToChildClickListener);
                expandableListView.setGroupIndicator(null);
                expandableListView.setAdapter(costomExppandableAdapter);

                expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        return false;
                    }
                });
                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                        String content = stuents.get(groupPosition).get(childPosition);
                        ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean childrenBean = stuents.get(groupPosition).get(childPosition);
                        childrenBean.setSelect(true);
                        hobbiesList.add(Collections.singletonList(childrenBean));
                        return false;
                    }
                });
                break;
        }
    }

    public List<List<ChooseHobbiseData.DataBean.ChildrenBeanX.ChildrenBean>> getHobbiesList(){
        return hobbiesList;
    }
}
