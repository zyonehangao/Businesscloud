package com.cloud.shangwu.businesscloud.im.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.inscripts.custom.EmojiTextView;

public class RightMessageViewHolder extends RecyclerView.ViewHolder{
    public EmojiTextView textMessage;
    public TextView messageTimeStamp;
    public ImageView rightArrow,messageStatus;
    public ImageView retry;
    public RightMessageViewHolder(View itemView) {
        super(itemView);
        rightArrow = itemView.findViewById(R.id.rightArrow);
        textMessage = itemView.findViewById(R.id.textViewMessage);
        messageStatus = itemView.findViewById(R.id.img_message_status);
        messageTimeStamp = itemView.findViewById(R.id.timestamp);
        retry = itemView.findViewById(R.id.textRetry);
    }

}
