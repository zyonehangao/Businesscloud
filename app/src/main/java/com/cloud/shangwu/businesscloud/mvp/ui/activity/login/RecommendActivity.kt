package com.cloud.shangwu.businesscloud.mvp.ui.activity.login

import android.view.View
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.base.BaseSwipeBackActivity
import com.cloud.shangwu.businesscloud.mvp.ui.adapter.CardAdapter
import kotlinx.android.synthetic.main.toolbar.*

import com.cloud.shangwu.businesscloud.widget.cardswipelayout.CardLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.cloud.shangwu.businesscloud.widget.cardswipelayout.CardConfig
import android.support.v7.widget.RecyclerView
import com.cloud.shangwu.businesscloud.widget.cardswipelayout.OnSwipeListener
import com.cloud.shangwu.businesscloud.widget.cardswipelayout.CardItemTouchHelperCallback
import android.support.v7.widget.DefaultItemAnimator
import android.widget.Toast


/**
 * Created by Administrator on 2018/12/5.
 */
class RecommendActivity :BaseSwipeBackActivity(){


    private val mList = mutableListOf(0)


    override fun attachLayoutRes(): Int = R.layout.activity_recommend

    override fun initData() {

    }

    override fun initView() {

        toolbar.run {
            title = ""
            toolbar_nam.run {
                text = getString(R.string.recommend)
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        mList.clear()
        mList.add(R.drawable.img_avatar_01);
        mList.add(R.drawable.img_avatar_02);
        mList.add(R.drawable.img_avatar_03);
        mList.add(R.drawable.img_avatar_04);
        mList.add(R.drawable.img_avatar_05);
        mList.add(R.drawable.img_avatar_06);
        mList.add(R.drawable.img_avatar_07);

        val recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = CardAdapter(mList)
        val cardCallback = CardItemTouchHelperCallback(recyclerView.adapter!!, mList)
        cardCallback.setOnSwipedListener(object : OnSwipeListener<Int> {

            override fun onSwiping(viewHolder: RecyclerView.ViewHolder, ratio: Float, direction: Int) {
                val myHolder = viewHolder as CardAdapter.MyViewHolder
                viewHolder.itemView.alpha = 1 - Math.abs(ratio) * 0.2f
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio))
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.likeImageView.setAlpha(Math.abs(ratio))
                } else {
                    myHolder.dislikeImageView.setAlpha(0f)
                    myHolder.likeImageView.setAlpha(0f)
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, o: Int?, direction: Int) {
                val myHolder = viewHolder as CardAdapter.MyViewHolder
                viewHolder.itemView.alpha = 1f
                myHolder.dislikeImageView.setAlpha(0f)
                myHolder.likeImageView.setAlpha(0f)
                Toast.makeText(this@RecommendActivity, if (direction == CardConfig.SWIPED_LEFT) "swiped left" else "swiped right", Toast.LENGTH_SHORT).show()
            }

            override fun onSwipedClear() {
                Toast.makeText(this@RecommendActivity, "data clear", Toast.LENGTH_SHORT).show()
                recyclerView.postDelayed({
                    initData()
                    recyclerView.adapter!!.notifyDataSetChanged()
                }, 3000L)
            }

        })
        val touchHelper = ItemTouchHelper(cardCallback)
        val cardLayoutManager = CardLayoutManager(recyclerView, touchHelper)
        recyclerView.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(recyclerView)


    }

    override fun start() {

    }


}