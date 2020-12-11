package com.example.databindsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.databindsample.databinding.ActSignInCalendarItem7thBinding
import com.example.databindsample.databinding.ActSignInCalendarItemBinding


/**
 * @author: zeting
 * @date: 2020/6/19
 * 签到适配器
 */
class SigningAdapter(var mList: MutableList<BindSingingItemObj>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 签到数据
//    var mList: MutableList<BindSingingItemObj>? = null

    /**
     * 刷新数据
     */
    fun notifyData(list: MutableList<BindSingingItemObj>) {
        this.mList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return mList?.get(position)?.type ?: BindSingingItemObj.TYPE_SIGN_DEFAULT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == BindSingingItemObj.TYPE_SIGN_GOLD) {
            var bindingOther = ActSignInCalendarItem7thBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)

            /**
             * 下面两张方式

            val bingding1: ActSignInCalendarItem7thBinding = ActSignInCalendarItem7thBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
            val bingding2: ActSignInCalendarItem7thBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.act_sign_in_calendar_item_7th, parent, false)

             */
            return Sign7ThViewHolder(bindingOther)
        } else {
            var bindingOther = ActSignInCalendarItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
            return SignViewHolder(bindingOther)
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var itemData = mList?.get(position)

        if (holder.itemViewType == BindSingingItemObj.TYPE_SIGN_GOLD) {
            (holder as Sign7ThViewHolder).binding.itemData = itemData
            (holder as Sign7ThViewHolder).binding.executePendingBindings()
        } else {
            (holder as SignViewHolder).binding.itemData = itemData
            (holder as SignViewHolder).binding.executePendingBindings()
        }
    }

    class SignViewHolder(itemBinding: ActSignInCalendarItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ActSignInCalendarItemBinding = itemBinding
    }

    class Sign7ThViewHolder(itemBinding: ActSignInCalendarItem7thBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ActSignInCalendarItem7thBinding = itemBinding
    }
}