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

import com.cloud.shangwu.businesscloud.mvp.model.bean.ChannelManage;
import com.cloud.shangwu.businesscloud.mvp.model.bean.ColorSuggestion;
import com.cloud.shangwu.businesscloud.mvp.model.db.SQLHelper;

import com.cloud.shangwu.businesscloud.widget.FloatingSearchView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;


import java.util.ArrayList;
import java.util.List;




/**
 * 频道管理
 * @Author RA
 * @Blog http://blog.csdn.net/vipzjyno1
 */
public class LablesActivity extends BaseSwipeBackActivity  {


    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
    boolean isMove = false;

    private TagFlowLayout mFlowLayout;
    private TagFlowLayout mFlowLayoutmore;

    private List<String> list1 = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();

    private TagAdapter<String> madapter;
    private TagAdapter<String> madapterMore;

    private Boolean islabelClicked=false;

    private ImageView mBack;

    private ImageView mSearch;

    private TextView mInput;

    private boolean mIsDarkSearchTheme = false;

    private FloatingSearchView mSearchView;

    /** 初始化数据*/
    public void initData() {

        list1.add("Java");
        list1.add("C++");
        list1.add("Python");
        list1.add("Swift");
        list1.add("你好，这是一个TAG");
        list1.add("PHP");
        list1.add("JavaScript");
        list1.add("Html");
        list1.add("Welcome to use AndroidTagView!");

        list2.add("China");
        list2.add("USA");
        list2.add("Austria");
        list2.add("Japan");
        list2.add("Sudan");
        list2.add("Spain");
        list2.add("UK");
        list2.add("Germany");
        list2.add("Niger");
        list2.add("Poland");
        list2.add("Norway");
        list2.add("Uruguay");
        list2.add("Brazil");
        list2.add("Java");
    }

    /** 初始化布局*/
    @SuppressLint("WrongViewCast")
    public void initView() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
//        mBack= findViewById(R.id.back);
//        mSearch=findViewById(R.id.search);
//        mInput=findViewById(R.id.input);

//        mBack.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


        mFlowLayoutmore = (TagFlowLayout) findViewById(R.id.id_flowlayout_more);
        ImageView left_action = (ImageView) mFlowLayoutmore.findViewById(R.id.left_action);
//        left_action.setOnFocusChangeListener();
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);

        List<ColorSuggestion> list = new ArrayList<ColorSuggestion>();

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {

            }

        });

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                Toast.makeText(LablesActivity.this,"s搜索",Toast.LENGTH_SHORT).show();
            }

//            @Override
//            public void onMenuItemSelected(MenuItem item) {
//
//            }
        });

//        mSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //获取数据
//            }
//        });



        mFlowLayoutmore = (TagFlowLayout) findViewById(R.id.id_flowlayout_more);

        madapter=new TagAdapter<String>(list1) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.subscribe_category_item,
                        mFlowLayout, false);
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.error1);
                drawable.setBounds(0, 0, 30,20);
                tv.setCompoundDrawables(null, null, drawable, null);
                tv.setCompoundDrawablePadding(25);
                tv.setText(list1.get(position));
                return tv;
            }
        };
        madapterMore=new TagAdapter<String>(list2) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.lab_more_item,
                    mFlowLayout, false);
            tv.setText(list2.get(position));
            return tv;
        }};

        mFlowLayout.setAdapter(madapter);
        mFlowLayoutmore.setAdapter(madapterMore);

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                int indexOf = list2.lastIndexOf(list1.get(position));
                View childAt = mFlowLayoutmore.getChildAt(indexOf);
                if (list2.contains(list1.get(position))){
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
                if (!list1.contains(list2.get(position))){
                    list1.add(list2.get(position));
                    madapter.notifyDataChanged();
                    view.setClickable(false);
                }
                return false;
            }
        });

    }


//    /** GRIDVIEW对应的ITEM点击监听接口  */
//    @Override
//    public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) {
//        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
//        if(isMove){
//            return;
//        }
//        switch (parent.getId()) {
//            case R.id.userGridView:
//                //position为 0，1 的不可以进行任何操作
//                if (position != 0 && position != 1) {
//                    final ImageView moveImageView = getView(view);
//                    if (moveImageView != null) {
//                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
//                        final int[] startLocation = new int[2];
//                        newTextView.getLocationInWindow(startLocation);
//                        final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
//                        otherAdapter.setVisible(false);
//                        //添加到最后一个
//                        otherAdapter.addItem(channel);
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
//                                try {
//                                    int[] endLocation = new int[2];
//                                    //获取终点的坐标
//                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
//                                    MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
//                                    userAdapter.setRemove(position);
//                                } catch (Exception localException) {
//                                }
//                            }
//                        }, 50L);
//                    }
//                }
//                break;
//            case R.id.otherGridView:
//                final ImageView moveImageView = getView(view);
//                if (moveImageView != null){
//                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
//                    final int[] startLocation = new int[2];
//                    newTextView.getLocationInWindow(startLocation);
//                    final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
//                    userAdapter.setVisible(false);
//                    //添加到最后一个
//                    userAdapter.addItem(channel);
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            try {
//                                int[] endLocation = new int[2];
//                                //获取终点的坐标
//                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
//                                MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
//                                otherAdapter.setRemove(position);
//                            } catch (Exception localException) {
//                            }
//                        }
//                    }, 50L);
//                }
//                break;
//            default:
//                break;
//        }
//    }
    /**
     * 点击ITEM移动动画
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final String moveChannel,
                          final FlowLayout clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (islabelClicked) {
//                    otherAdapter.setVisible(true);
                    list1.remove(list1.indexOf(moveChannel));
                    madapter.notifyDataChanged();
                    madapterMore.notifyDataChanged();
                    mFlowLayoutmore.getChildAt(list2.indexOf(moveChannel)).setClickable(true);
//                    madapter.notifyDataSetChanged();
//                    userAdapter.remove();
                }else{
                    list1.add(moveChannel);
                    madapter.notifyDataChanged();
                    madapterMore.notifyDataChanged();
                    mFlowLayoutmore.getChildAt(list2.indexOf(moveChannel)).setClickable(false);
//                    userAdapter.setVisible(true);
//                    userAdapter.notifyDataSetChanged();
//                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     * @param view
     * @return
     */
    private TextView getView(View view) {
        view.destroyDrawingCache();
//        view.setDrawingCacheEnabled(true);
//        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
//        view.setDrawingCacheEnabled(false);
        TextView iv = new TextView(this);
//        iv.setImageBitmap(cache);
        return iv;
    }

    /** 退出时候保存选择后数据库的设置  */
    private void saveChannel() {

        ChannelManage.getManage(new SQLHelper(App.instance)).deleteAllChannel();
        ChannelManage.getManage(new SQLHelper(App.instance)).getUserChannel();
        ChannelManage.getManage(new SQLHelper(App.instance)).getOtherChannel();
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

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    public class TagRecyclerViewAdapter
//            extends RecyclerView.Adapter<TagRecyclerViewAdapter.TagViewHolder> {
//
//        private Context mContext;
//        private String[] mData;
//        private View.OnClickListener mOnClickListener;
//
//        public TagRecyclerViewAdapter(Context context, String[] data) {
//            this.mContext = context;
//            this.mData = data;
//        }
//
//        @Override
//        public int getItemCount() {
//            return 10;
//        }
//
//        @Override
//        public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new TagViewHolder(LayoutInflater.from(mContext)
//                    .inflate(R.layout.view_recyclerview_item, parent, false), mOnClickListener);
//        }
//
//        @Override
//        public void onBindViewHolder(TagViewHolder holder, int position) {
//            holder.tagContainerLayout.setTags(mData);
//
//        }
//
//        public void setOnClickListener(View.OnClickListener listener) {
//            this.mOnClickListener = listener;
//        }
//
//        class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//            TagContainerLayout tagContainerLayout;
//
//
//            public TagViewHolder(View v, View.OnClickListener listener) {
//                super(v);
//                this.clickListener = listener;
//                tagContainerLayout = (TagContainerLayout) v.findViewById(R.id.tagcontainerLayout);
//                button = (Button) v.findViewById(R.id.button);
////                v.setOnClickListener(this);
//            }
//
//            @Override
//            public void onClick(View v) {
//                if (clickListener != null) {
//                    clickListener.onClick(v);
//                }
//            }
//        }
//    }
}
