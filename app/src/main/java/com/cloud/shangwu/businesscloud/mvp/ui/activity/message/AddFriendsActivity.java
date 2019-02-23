package com.cloud.shangwu.businesscloud.mvp.ui.activity.message;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.base.BaseActivity;
import com.cloud.shangwu.businesscloud.constant.Constant;
import com.cloud.shangwu.businesscloud.mvp.contract.AddFriendsContract;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean;
import com.cloud.shangwu.businesscloud.mvp.presenter.AddFriendsPresenter;
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.AddFriendAdapter;
import com.cloud.shangwu.businesscloud.widget.CommomDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsActivity extends BaseActivity implements AddFriendsContract.View {

    private AddFriendsPresenter mPresenter;
    private int page = 1;
    private int count = 15;
    private SmartRefreshLayout mRefreshLayout;
    private List<ToDoListBean.DataBean.ListBean> mList;
    private RecyclerView mRlView;
    private AddFriendAdapter mAdapter;
    private String mUid;
    private String mString;
    private int num; //总条目

    @Override
    protected int attachLayoutRes() {
        return R.layout.acticity_addfriends;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mRlView = (RecyclerView) findViewById(R.id.rlView);
        mRlView.setLayoutManager(new LinearLayoutManager(this));
        RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        EditText et_search = (EditText) findViewById(R.id.et_search);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh);
        SharedPreferences sharedPreferences = getSharedPreferences("businesscloud", MODE_PRIVATE);
        mUid = sharedPreferences.getString(Constant.UID, "");
        mList = new ArrayList<>();
        mAdapter = new AddFriendAdapter(R.layout.item_addfriends, mList);
        mRlView.setAdapter(mAdapter);
        mPresenter = new AddFriendsPresenter(this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {



            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mList != null) {
                        mList.clear();
                    }
                    mString = v.getText().toString();
                    if (TextUtils.isEmpty(mString)) {
                        Toast.makeText(AddFriendsActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        mPresenter.searchFriends(page, mString, count,true);
                    }
                }

                return false;
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_add) {
                    CommomDialog dialog = new CommomDialog(AddFriendsActivity.this, R.style.dialog, "确定添加好友吗？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
//                                mPresenter.sendFriendsMessage(mAdapter.getItem(position).getType(),mAdapter.getItem(position).getUid()+"","",mUid);
                                int fuid = mAdapter.getItem(position).getUid();
                                mPresenter.sendFriendsMessage("0", fuid + "", "111", 18 + "");
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }).setNegativeButton("取消").setPositiveButton("确定").setTitle("提示");
                    dialog.show();
                }
            }
        });

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.searchFriends(page,mString,count,false);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void searchFriends(@NotNull ToDoListBean data, boolean isrefresh) {

            if (data.getCode() == 200) {
                num = data.getData().getPageInfo().getRecordCount();
                page++;
                //加载更多
                if (!isrefresh) {
                    mList.addAll(data.getData().getList());
                    mAdapter.notifyDataSetChanged();
                    if (mList.size() >= num) {
                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                    mRefreshLayout.finishLoadMore(1500);
                    }

                } else {

                    mList.addAll(data.getData().getList());
                    mAdapter.notifyDataSetChanged();
                }

            }

    }

    @Override
    public void sendFriendMessage(int code) {
        if (code == 200) {
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(@NotNull String errorMsg) {

    }

}
