package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.constant.Constant;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private final Context context;
    private final List<ToDoListBean.DataBean.ListBean> list;
    private onClickListener listener;


    public  void  setListener(onClickListener  listener){
        this.listener=listener;
    }

    public ToDoListAdapter(Context context, List<ToDoListBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todolist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (list.get(i).getPortrait() != null) {
            Glide.with(context).load(Constant.BASE_URL + "business/image?image=" + list.get(i).getPortrait()).into(viewHolder.mIv_head);
        }
        viewHolder.mTv_name.setText(list.get(i).getUsername());
        viewHolder.mTv_message.setText("没有附件信息");

        viewHolder.mIv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onDel(list.get(i).getUid());
                }
            }
        });

        viewHolder.mIv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onChoose(list.get(i).getUid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView mIv_head;
        private final TextView mTv_name;
        private final TextView mTv_message;
        private final ImageView mIv_del;
        private final ImageView mIv_choose;

        public ViewHolder(View view) {
            super(view);
            mIv_head = view.findViewById(R.id.iv_head);
            mTv_name = view.findViewById(R.id.tv_name);
            mTv_message = view.findViewById(R.id.tv_message);
            mIv_del = view.findViewById(R.id.iv_del);
            mIv_choose = view.findViewById(R.id.iv_choose);

        }
    }


    public interface onClickListener {
        void onChoose(int position);

        void onDel(int position);
    }


//    public ToDoListAdapter(int layoutResId, @Nullable List<ToDoListBean.DataBean.ListBean> data) {
//        super(layoutResId, data);
//
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, ToDoListBean.DataBean.ListBean item) {
//        if (item.getPortrait()!=null){
//            Glide.with(mContext).load(Constant.BASE_URL+"business/image?image=" + item.getPortrait()).into((CircleImageView) helper.getView(R.id.iv_head));
//        }
//        helper.setText(R.id.tv_name,item.getUsername());
//        helper.setText(R.id.tv_message,"没有添加信息");
//        helper.addOnClickListener(R.id.iv_del)
//                .addOnClickListener(R.id.iv_choose);
//    }


}
