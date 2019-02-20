package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToDoListAdapter extends BaseQuickAdapter<ToDoListBean.DataBean.ListBean, BaseViewHolder> {

    public ToDoListAdapter(int layoutResId, @Nullable List<ToDoListBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToDoListBean.DataBean.ListBean item) {
        if (item.getPortrait()!=null){
            Glide.with(mContext).load("http://13.231.212.214/business/image?image=" + item.getPortrait()).into((CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name,item.getUsername());
        helper.setText(R.id.tv_message,"没有添加信息");
        helper.addOnClickListener(R.id.iv_del)
                .addOnClickListener(R.id.iv_choose);
    }
}
