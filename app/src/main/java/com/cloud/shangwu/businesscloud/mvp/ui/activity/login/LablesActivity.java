package com.cloud.shangwu.businesscloud.mvp.ui.activity.login;


import android.annotation.SuppressLint;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.content.ContextCompat;

import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.util.Log;
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
    boolean isMove = false;

    private TagFlowLayout mFlowLayout;
    private TagFlowLayout mFlowLayoutmore;

    private List<String> list1 = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();

    private TagAdapter<String> madapter;
    private LabelAdapter madapterMore;

    private Boolean islabelClicked = false;

    private ImageView mBack;

    private ImageView mSearch;

    private TextView mInput;

    private boolean mIsDarkSearchTheme = false;

    private FloatingSearchView mSearchView;
    private LayoutInflater mInflater;

    private LabelHotPresenter mPresenter = getPresenter();

    private LabelHotPresenter getPresenter() {
        return new LabelHotPresenter();
    }

    /**
     * 初始化数据
     */
    public void initData() {

//        list1.add("Java");
//        list1.add("C++");
//        list1.add("Python");
//        list1.add("Swift");
//        list1.add("你好，这是一个TAG");
//        list1.add("PHP");
//        list1.add("JavaScript");
//        list1.add("Html");
//        list1.add("Welcome to use AndroidTagView!");
//
//        list2.add("China");
//        list2.add("USA");
//        list2.add("Austria");
//        list2.add("Japan");
//        list2.add("Sudan");
//        list2.add("Spain");
//        list2.add("UK");
//        list2.add("Germany");
//        list2.add("Niger");
//        list2.add("Poland");
//        list2.add("Norway");
//        list2.add("Uruguay");
//        list2.add("Brazil");
//        list2.add("Java");
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

        List<ColorSuggestion> list = new ArrayList<ColorSuggestion>();


        mSearchView.setOnSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mFlowLayoutmore = (TagFlowLayout) findViewById(R.id.id_flowlayout_more);

    }


    @Override
    public void onBackPressed() {
//        saveChannel();
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
        for (LabelHot hot : data) {
            list1.add(hot.getContext());
            list2.add(hot.getContext());
        }
        list1.remove(0);
        list1.remove(1);

        madapter = new TagAdapter<String>(list1) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.subscribe_category_item,
                        mFlowLayout, false);
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.error1);
                drawable.setBounds(0, 0, 30, 20);
                tv.setCompoundDrawables(null, null, drawable, null);
                tv.setCompoundDrawablePadding(25);
                tv.setText(list1.get(position));
                return tv;
            }
        };


        Log.i("test","do1");

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                int indexOf = list2.lastIndexOf(list1.get(position));
                View childAt = mFlowLayoutmore.getChildAt(indexOf);
                if (list2.contains(list1.get(position))) {
                    childAt.setClickable(true);
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
                if (!list1.contains(list2.get(position))) {
                    list1.add(list2.get(position));
                    madapter.notifyDataChanged();
                    view.setClickable(false);
                }
                return false;
            }
        });

        madapterMore = new LabelAdapter(list2) {
            @Override
            public void setClickAble(int position, View v) {
                if (list1.contains(list2.get(position))){
                    v.setClickable(false);
                }else {
                    v.setClickable(true);
                }
            }

            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.lab_more_item,
                        mFlowLayout, false);
                tv.setText(list2.get(position));
                return tv;
            }

            @Override
            public boolean setSelected(int position, Object o) {
                if (list1.contains(list2.get(position))){
                    return true;
                }
                return false;
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