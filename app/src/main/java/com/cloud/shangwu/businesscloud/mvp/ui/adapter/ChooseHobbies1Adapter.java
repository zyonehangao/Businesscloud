package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChooseHobbiseData;

import java.util.List;

public class ChooseHobbies1Adapter extends BaseMultiItemQuickAdapter<ChooseHobbiseData.DataBean.ChildrenBeanX,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChooseHobbies1Adapter(List<ChooseHobbiseData.DataBean.ChildrenBeanX> data) {
        super(data);
        addItemType(ChooseHobbiseData.DataBean.ChildrenBeanX.Companion.getHeard(), R.layout.item_heard_view);
        addItemType(ChooseHobbiseData.DataBean.ChildrenBeanX.Companion.getText(), R.layout.item_text_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChooseHobbiseData.DataBean.ChildrenBeanX item) {

    }
}
