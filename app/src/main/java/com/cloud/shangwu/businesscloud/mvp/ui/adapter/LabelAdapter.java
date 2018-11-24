package com.cloud.shangwu.businesscloud.mvp.ui.adapter;

import android.view.View;

import com.cloud.shangwu.businesscloud.mvp.contract.MainContract;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/11/25.
 */

public abstract class LabelAdapter<T> extends TagAdapter {
    public LabelAdapter(List datas) {
        super(datas);
    }

    public abstract void setClickAble(int position, View v) ;

}
