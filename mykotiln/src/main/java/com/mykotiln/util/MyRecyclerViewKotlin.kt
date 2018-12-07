package com.mykotiln.util

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mykotiln.R


/**
 * Created by allen on 18/10/11.
 */

class MyRecyclerViewKotlin : RecyclerView {


    private var mAdapterRecycle: AdapterRecycle? = null
    private var mContext: Context? = null
    private var mList: List<MyRecycleListItem>? = null
    private var mOnClick: MyRecycleOnClick? = null


    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initView(context)
    }


    fun initView(mContext: Context) {
        this.mContext = mContext
    }


    fun setMyAdapter(mList: List<MyRecycleListItem>, mOnClick: MyRecycleOnClick) {
        this.mList = mList
        this.mOnClick = mOnClick

        mAdapterRecycle = AdapterRecycle()
        layoutManager = LinearLayoutManager(mContext)
        //添加Android自带的分割线
        addItemDecoration(DividerItemDecoration(mContext!!, DividerItemDecoration.VERTICAL))
        adapter = mAdapterRecycle

    }

    inner class AdapterRecycle : RecyclerView.Adapter<DateHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle, parent, false)
            val viewHolder = DateHolder(view)
            return viewHolder
        }

        override fun onBindViewHolder(holder: DateHolder, position: Int) {
            holder.mTextview.text = mList!![position].itemId.toString() + " : " + mList!![position].itemValue
            holder.mTextview.setOnClickListener { mOnClick!!.onClickListener(mList!![position].itemId) }
        }

        override fun getItemCount(): Int {
            return mList!!.size
        }
    }

    inner class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var mTextview: TextView

        init {
            mTextview = itemView.findViewById(R.id.textView) as TextView
        }
    }

    interface MyRecycleOnClick {
        fun onClickListener(position: Int)
    }

    inner class MyRecycleListItem(var itemId: Int, var itemValue: String)

}
