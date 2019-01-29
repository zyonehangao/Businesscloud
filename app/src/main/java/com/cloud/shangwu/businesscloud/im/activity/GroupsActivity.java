package com.cloud.shangwu.businesscloud.im.activity;

import android.view.View;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.base.BaseActivity;
import com.cloud.shangwu.businesscloud.im.fragment.GroupFragment;


/**
 * Created by Administrator on 2019/1/29.
 */

public class GroupsActivity extends BaseActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_group;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.container,new GroupFragment()).commit();
        findViewById(R.id.iv_black).setVisibility(View.GONE);

    }

    @Override
    public void start() {

    }
}
