package com.cloud.shangwu.businesscloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class LeftActionImagView extends android.support.v7.widget.AppCompatImageView {

    public LeftActionImagView(Context context) {
        super(context);

    }

    public LeftActionImagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeftActionImagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnFinishListener mfinishListener;

    public interface OnFinishListener {

        void onFinish();
    }

    public void setOnFinishListener(OnFinishListener listener){
        this.mfinishListener = listener;
    }
}
