package com.cloud.shangwu.businesscloud.mvp.ui.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cloud.shangwu.businesscloud.R
import com.cloud.shangwu.businesscloud.mvp.model.bean.PostData

/**
 * Created by Administrator on 2018/12/2.
 */
class PostAdapter(var list: MutableList<PostData>?): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(list!![p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        var item=LayoutInflater.from(p0.context).inflate(R.layout.item_position,p0,false)
        item.setOnClickListener(View.OnClickListener {
            Toast.makeText(p0.context,"click"+p1,Toast.LENGTH_SHORT).show()
        })
        return ViewHolder(item)
    }


    override fun getItemCount(): Int = list!!.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(postData: PostData) {
            var tv_position : TextView = view.findViewById(R.id.tv_position)
            var company : TextView = view.findViewById(R.id.company)
            var area : TextView = view.findViewById(R.id.area)
            var experience : TextView = view.findViewById(R.id.experience)
            var edution : TextView = view.findViewById(R.id.edution)
            var wage : TextView = view.findViewById(R.id.wage)

            tv_position.text=postData.name
            company.text=postData.company
            area.text=postData.location
            experience.text=postData.exception
            edution.text=postData.edution
            wage.text=postData.wage
        }
    }

}