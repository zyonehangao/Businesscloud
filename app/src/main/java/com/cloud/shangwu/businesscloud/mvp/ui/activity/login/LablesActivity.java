package com.cloud.shangwu.businesscloud.mvp.ui.activity.login;


import android.annotation.SuppressLint;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.content.ContextCompat;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

//import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.cloud.shangwu.businesscloud.R;
import com.cloud.shangwu.businesscloud.app.App;
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity;

import com.cloud.shangwu.businesscloud.mvp.contract.LabelHotContract;
import com.cloud.shangwu.businesscloud.mvp.contract.LoginContract;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ChannelManage;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ColorSuggestion;
import com.cloud.shangwu.businesscloud.mvp.model.bean.LabelHot;

import com.cloud.shangwu.businesscloud.mvp.model.bean.LoginData;
import com.cloud.shangwu.businesscloud.mvp.model.db.SQLHelper;

import com.cloud.shangwu.businesscloud.mvp.presenter.LabelHotPresenter;
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.LabelAdapter;
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.SearchResultsListAdapter;
import com.cloud.shangwu.businesscloud.utils.DialogUtil;
import com.cloud.shangwu.businesscloud.widget.FloatingSearchView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;




/**
 * 频道管理
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class LablesActivity extends BaseSwipeBackActivity implements LabelHotContract.View {


    /**
     * 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。
     */

    private TagFlowLayout mFlowLayout;
    private TagFlowLayout mFlowLayoutmore;

    private TextView mCommit;

    private List<LabelHot> list1 = new ArrayList<LabelHot>();
    private List<LabelHot> list2 = new ArrayList<LabelHot>();

    private TagAdapter<LabelHot> madapter;
    private LabelAdapter madapterMore;

    private FloatingSearchView mSearchView;
    private LayoutInflater mInflater;

    private LabelHotPresenter mPresenter = getPresenter();

    private RecyclerView mSearchResultsList;
    private SearchResultsListAdapter mSearchResultsAdapter;

    private ScrollView mMain;

//    private String mLastQuery = "";

    private LabelHotPresenter getPresenter() {
        return new LabelHotPresenter();
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 初始化布局
     */
    @SuppressLint("WrongViewCast")
    public void initView() {
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        mPresenter.attachView(this);
        mFlowLayoutmore = (TagFlowLayout) findViewById(R.id.id_flowlayout_more);
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mSearchResultsList = (RecyclerView) findViewById(R.id.search_results_list);
        mCommit= findViewById(R.id.commit);
        mMain=(ScrollView) findViewById(R.id.main);
        mSearchResultsAdapter = new SearchResultsListAdapter();

        mSearchResultsAdapter.setItemsOnClickListener(new SearchResultsListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mSearchView.setSearchText(list2.get(position).getContext());
                mMain.bringToFront();
            }
        });
        mSearchResultsList.setAdapter(mSearchResultsAdapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(LablesActivity.this,LinearLayoutManager.VERTICAL,true));

        mFlowLayoutmore = (TagFlowLayout) findViewById(R.id.id_flowlayout_more);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        mSearchView.setOnSearchClickListener(new FloatingSearchView.OnSearchClickListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                list2.remove(0);
                mSearchResultsAdapter.swapData(list2);
            }

            @Override
            public void onSearchAction(String currentQuery) {
                mSearchResultsList.bringToFront();
                mSearchResultsAdapter.swapData(list2);
            }
        });

    }


    @Override
    public void onBackPressed() {
        mMain.bringToFront();
        super.onBackPressed();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.subscribe_activity;
    }

    @Override
    public void start() {
        mPresenter.labelHot(45, 3);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void labelSuccess(List<LabelHot> data) {
        Log.i("test","do2");
        list2.addAll(data);
        //选中的标签
        madapter = new TagAdapter<LabelHot>(list1) {
            @Override
            public View getView(FlowLayout parent, int position, LabelHot labelHot) {
                TextView tv = ((TextView) mInflater.inflate(R.layout.subscribe_category_item, parent, false));
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.error1);
                drawable.setBounds(0, 2, 50,50);
                tv.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
                tv.setCompoundDrawables(null, null, drawable, null);
                tv.setCompoundDrawablePadding(0);
                tv.setText(list1.get(position).getContext());
                return   tv;
            }

        };

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                LabelHot info = list1.get(position);
                for (LabelHot labelHot : list2) {
                    if (info.getLid()==labelHot.getLid()){
                        labelHot.setSelect(false);
                    }
                }
                list1.remove(position);
                madapter.notifyDataChanged();
                madapterMore.notifyDataChanged();
                return false;

            }
        });

        mFlowLayoutmore.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (!list2.get(position).getSelect()){
                    list2.get(position).setSelect(true);
                    list1.add(list2.get(position));
                }
                madapter.notifyDataChanged();
                madapterMore.notifyDataChanged();
                return true;
            }
        });



        madapterMore = new LabelAdapter(list2) {
            @Override
            public void setClickAble(int position, View v) {

            }

            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.lab_more_item,
                        mFlowLayout, false);
                tv.setText(list2.get(position).getContext());
                for (LabelHot labelHot : list2) {
                    if (labelHot.getSelect()){
                       tv.setEnabled(false);
                    }else {
                        tv.setEnabled(true);
                    }
                }

                return tv;
            }

            @Override
            public boolean setSelected(int position, Object o) {

                return  list2.get(position).getSelect();
            }

        };

        mFlowLayout.setAdapter(madapter);
        mFlowLayoutmore.setAdapter(madapterMore);

    }

    @Override
    public void labelFail() {

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
