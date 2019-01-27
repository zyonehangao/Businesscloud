package com.cloud.shangwu.businesscloud.im.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.inscripts.custom.EmojiTextView;
import com.inscripts.custom.RoundedImageView;

public class LeftMessageViewHolder extends RecyclerView.ViewHolder {
    public EmojiTextView textMessage;
    public TextView messageTimeStamp;
    public TextView senderName;
    public ImageView leftArrow;
    public RoundedImageView avatar;
    public LeftMessageViewHolder(View leftTextMessageView) {
        super(leftTextMessageView);
        textMessage = leftTextMessageView.findViewById(R.id.textViewMessage);
        messageTimeStamp = leftTextMessageView.findViewById(R.id.timeStamp);
        leftArrow = leftTextMessageView.findViewById(R.id.leftArrow);
        avatar = leftTextMessageView.findViewById(R.id.imgAvatar);
        senderName = leftTextMessageView.findViewById(R.id.senderName);
    }
}
