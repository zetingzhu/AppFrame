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
import kotlinx.android.synthetic.main.item_recycle.*

/**
 * Created by allen on 18/11/15.
 */

class MyRecyclerViewKotlin : RecyclerView{

    /**
     * var 声明为可变的
     * val 声明为不可变
     */
    var mAdapterRecycle: AdapterRecycle ? = null
    var mList : List<MyRecycleListItem>? = null
    var mOnClick: MyRecycleOnClick? = null
    var mContext : Context? = null

    /**
     * 次构造函数, 类也可以有二级构造函数，需要加前缀 constructor:
     * context: Context? 后面加？ 表示可为空
     * */
    constructor(context: Context?) : super(context){
        initView(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView(context)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle){
        initView(context)
    }

    fun initView(mContext : Context?){
        this.mContext = mContext
    }

    /**
     * 设备适配器
     */
    fun setMyAdapter(mList : List<MyRecycleListItem> , mOnClick: MyRecycleOnClick){
        this.mList = mList
        this.mOnClick = mOnClick
        mAdapterRecycle = AdapterRecycle()
        layoutManager = LinearLayoutManager(mContext)
        addItemDecoration(DividerItemDecoration(mContext , DividerItemDecoration.VERTICAL))
        adapter = mAdapterRecycle
    }

    /**
     * 定义一个适配器
     */
    /**内部类 内部类使用 inner 关键字来表示。 */
    inner class AdapterRecycle : RecyclerView.Adapter<DateHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DateHolder {

            var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_recycle , parent , false)
            var viewHolder = DateHolder(view)
            return viewHolder
        }

        override fun onBindViewHolder(holder: DateHolder?, position: Int) {
            holder!!.mTextview!!.text =  mList!![position].itemId.toString() + " : " + mList!![position].itemValue.toString()
            holder.mTextview!!.setOnClickListener(object :View.OnClickListener {
                override fun onClick(v: View?) {
                    mOnClick!!.onClickListener(mList!![position].itemId!!)
                }
            })
        }

        override fun getItemCount(): Int {
               return mList!!.size
        }
    }

    /**
     * 定义一个viewholder
     */
    inner class DateHolder(itemView: View ) : ViewHolder(itemView) {
        /** internal   // 同一个模块中可见 */
        internal var mTextview: TextView? = null
        init {
            mTextview = itemView.findViewById(R.id.textView) as TextView
        }
    }

}