package com.cloud.shangwu.businesscloud.mvp.ui.activity.message;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.base.BaseActivity;

public class AddFriendsActivity extends BaseActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.acticity_addfriends;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        RecyclerView rlView = (RecyclerView) findViewById(R.id.rlView);
        RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        EditText et_search = (EditText) findViewById(R.id.et_search);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){

                }

                return false;
            }
        });
    }

    @Override
    public void start() {

    }
}
