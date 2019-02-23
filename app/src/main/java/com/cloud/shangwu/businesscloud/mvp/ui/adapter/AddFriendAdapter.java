package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.constant.Constant;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFriendAdapter extends BaseQuickAdapter<ToDoListBean.DataBean.ListBean, BaseViewHolder> {
    public AddFriendAdapter(int layoutResId, @Nullable List<ToDoListBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToDoListBean.DataBean.ListBean item) {
        if (item.getPortrait() != null) {
            Glide.with(mContext).load(Constant.BASE_URL + "business/image?image=" + item.getPortrait()).into((CircleImageView) helper.getView(R.id.iv_head));
        }
        switch (item.getType()) {
            case "0":
                helper.getView(R.id.iv1).setVisibility(View.INVISIBLE);
                helper.getView(R.id.iv2).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_type,item.getUsername());
                helper.setText(R.id.tv_message,item.getPosition());

                break;
            case "1":
                helper.getView(R.id.iv1).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv2).setVisibility(View.INVISIBLE);

                helper.setText(R.id.tv_type,item.getCompanyName());
                helper.setText(R.id.tv_message,item.getIntro());
                break;
        }
        helper.addOnClickListener(R.id.tv_add);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
