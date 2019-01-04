package com.cloud.shangwu.businesscloud.im.ui.widget;

import android.content.Context;
import android.util.AttributeSet;


import com.cloud.shangwu.businesscloud.R;
import com.quickblox.ui.kit.chatmessage.adapter.widget.MessageTextView;


/**
 * Created by Administrator on 2018/12/16.
 */

public class MessageTextViewLeft extends MessageTextView {
    public MessageTextViewLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void setLinearSide() {
        LayoutParams layoutParams = (LayoutParams)this.frameLinear.getLayoutParams();
        layoutParams.gravity = 3;
        this.frameLinear.setLayoutParams(layoutParams);
    }

    protected void setTextLayout() {
        this.viewTextStub.setLayoutResource(R.layout.widget_text_msg_left);
//        this.layoutStub = (LinearLayout)this.viewTextStub.inflate();
    }
}
