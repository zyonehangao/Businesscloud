package com.cloud.shangwu.businesscloud.mvp.ui.activity.message;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.base.BaseActivity;
import com.cloud.shangwu.businesscloud.constant.Constant;
import com.cloud.shangwu.businesscloud.mvp.contract.ToDoListContract;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ToDoListBean;
import com.cloud.shangwu.businesscloud.mvp.presenter.ToDoListPresenter;
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.ToDoListAdapter;
import com.cloud.shangwu.businesscloud.utils.Preference;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;



public class ToDoListActivity extends BaseActivity implements ToDoListContract.View{

    private RecyclerView mRl_view;
    private int num = 0;  //总数
    private int count = 15; //每次返回个数
    private int page = 1;

    private ToDoListPresenter mPresenter;
    private List<ToDoListBean.DataBean.ListBean> mList;
    private String mUid;
    private ToDoListAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private boolean isLoad = false;

    @Override
    protected int attachLayoutRes() {
        return R.layout.acticity_todolist;
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
    }

    @Override
    public void initView() {
        mRl_view = (RecyclerView) findViewById(R.id.rlView);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh);
        mAdapter = new ToDoListAdapter(this, mList);
        SharedPreferences sharedPreferences = getSharedPreferences("businesscloud", MODE_PRIVATE);
        mUid = sharedPreferences.getString(Constant.UID, "");
        mRl_view.setLayoutManager(new LinearLayoutManager(this));
        mRl_view.setAdapter(mAdapter);
        mPresenter = new ToDoListPresenter(this);
        mPresenter.getToDoList(page, count, mUid, true);
        //下拉刷新
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                page = 1;
                mPresenter.getToDoList(page, count, mUid, true);
                mRefreshLayout.finishRefresh(1500);
            }
        });
        //加载更多
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                mPresenter.getToDoList(page, count, mUid, false);
            }
        });


//        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (view.getId() == R.id.iv_del) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
//                    builder.setTitle("提示")
//                            .setMessage("是否拒绝添加好友？")
//                            .setPositiveButton("拒绝", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //拒绝添加
//                                    ToDoListBean.DataBean.ListBean item = (ToDoListBean.DataBean.ListBean) adapter.getItem(position);
//                                    mPresenter.addFriends(mUid, item.getUid(), 0);
//                                }
//                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//
//                } else if (view.getId() == R.id.iv_choose) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
//                    builder.setTitle("提示")
//                            .setMessage("是否同意添加好友？")
//                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //同意添加
//                                    ToDoListBean.DataBean.ListBean item = (ToDoListBean.DataBean.ListBean) adapter.getItem(position);
//                                    mPresenter.addFriends(mUid, item.getUid(), 2);
//                                }
//                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//
//                }
//            }
//        });
    }


    //加载更多
    private void loadMore(int pages) {
        mPresenter.getToDoList(pages, count, mUid, false);
    }

    @Override
    public void getList(@NotNull ToDoListBean data, boolean isReresh) {
        if (data.getCode() == 200) {
            num = data.getData().getPageInfo().getRecordCount();
            page++;
            //加载更多
            if (!isReresh) {
                mList.addAll(data.getData().getList());
                mAdapter.notifyDataSetChanged();
                if (mList.size() >= num) {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadMore(1500);
                }

            } else {
                if (mList != null) {
                    mList.clear();
                }
                mList.addAll(data.getData().getList());
                mAdapter.notifyDataSetChanged();
            }

        }
        mAdapter.setListener(new ToDoListAdapter.onClickListener() {
            @Override
            public void onChoose(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
                    builder.setTitle("提示")
                            .setMessage("是否同意添加好友？")
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //同意添加
                                    mPresenter.addFriends(mUid, position, 2);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            }

            @Override
            public void onDel(int position) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
                    builder.setTitle("提示")
                            .setMessage("是否拒绝添加好友？")
                            .setPositiveButton("拒绝", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //拒绝添加
                                    mPresenter.addFriends(mUid, position, 0);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            }
        });
    }


    @Override
    public void start() {

    }


    @Override
    public void getError() {

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


    @Override
    public void addFriends(int code) {
        if (code == 200) {
            mRefreshLayout.autoRefresh();
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        }
    }


}
