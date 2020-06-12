package com.trade.eight.moudle.initDialog

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import frame.zzt.com.appframe.R
import frame.zzt.com.appframe.singleList.RecyclerAdapter

/**
 * @author: zeting
 * @date: 2020/5/28
 * 初始化对话框适配器
 */
class InitDialogAdapter : RecyclerAdapter() {

    var dataList: MutableList<InitTypDataList>? = null

    fun setData(dataList: MutableList<InitTypDataList>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        var itemType = super.getItemViewType(position)
        if (itemType == ITEM_TYPE_CONTENT) {
            var pos = convert(position)
            itemType = dataList?.get(pos)?.itemType ?: ITEM_TYPE_CONTENT
        }
        return itemType
    }


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == InitTypDataList.ITEM_TYPE_CREDIT_GENERAL ||
                viewType == InitTypDataList.ITEM_TYPE_CREDIT_FOR_PRODUCT) {
            return InitCreditViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.init_dialog_item_credit, parent, false))
        } else if (viewType == InitTypDataList.ITEM_TYPE_RECHARGE ||
                viewType == InitTypDataList.ITEM_TYPE_TRADE) {
            return InitTradeViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.init_dialog_item_trade, parent, false))
        } else {
            return InitVipViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.init_dialog_item_vip, parent, false))

        }
    }

    override fun getContentItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun getItem(position: Int): Any? {
        return dataList?.get(convert(position))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderView(position) || isBottomView(position)) {
            return
        }
        val viewType = holder.itemViewType
        var index = convert(position)
        if (index >= 0) {
            var dataItem = dataList?.get(index)
            if (viewType == InitTypDataList.ITEM_TYPE_CREDIT_GENERAL) {
                var creditViewHolder: InitCreditViewHolder = holder as InitCreditViewHolder
                creditViewHolder.tv_init_amount.text = dataItem?.creditAmount

            } else if (viewType == InitTypDataList.ITEM_TYPE_CREDIT_FOR_PRODUCT) {
                var creditViewHolder: InitCreditViewHolder = holder as InitCreditViewHolder
                creditViewHolder.tv_init_amount.text = dataItem?.creditAmount
                creditViewHolder.btn_init_trade.text = dataItem?.creditAmount

            } else if (viewType == InitTypDataList.ITEM_TYPE_RECHARGE) {
                var tradeViewHolder = holder as InitTradeViewHolder
                tradeViewHolder.tv_init_amount.text = dataItem?.tradeAmount
                tradeViewHolder.tv_init_amount_give.text = dataItem?.giveAmount + dataItem?.giveProduct

            } else if (viewType == InitTypDataList.ITEM_TYPE_TRADE) {
                var tradeViewHolder = holder as InitTradeViewHolder
                tradeViewHolder.tv_init_amount.text = dataItem?.tradeAmount
                tradeViewHolder.tv_init_amount_give.text = dataItem?.giveAmount

            } else if (viewType == InitTypDataList.ITEM_TYPE_VIP_DAY_7) {
                var vipViewHolder = holder as InitVipViewHolder
                vipViewHolder.tv_init_vip_title.text = dataItem?.vipTitle
                vipViewHolder.tv_init_vip_desc.text = dataItem?.vipDesc

            }
        }
    }


    /**
     * vip
     */
    inner class InitVipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn_init_buy = itemView.findViewById<Button>(R.id.btn_init_buy)
        var tv_init_vip_title = itemView.findViewById<TextView>(R.id.tv_init_vip_title)
        var tv_init_vip_desc = itemView.findViewById<TextView>(R.id.tv_init_vip_desc)
    }

    /**
     * 通用卷，产品卷
     */
    inner class InitCreditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rl_init_bg = itemView.findViewById<RelativeLayout>(R.id.rl_init_bg)
        var btn_init_trade = itemView.findViewById<Button>(R.id.btn_init_trade)
        var tv_init_amount = itemView.findViewById<TextView>(R.id.tv_init_amount)
        var tv_init_amount_credit = itemView.findViewById<TextView>(R.id.tv_init_amount_credit)
    }

    /**
     * 充值卷，交易卷
     */
    inner class InitTradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rl_init_bg = itemView.findViewById<RelativeLayout>(R.id.rl_init_bg)
        var btn_init_trade = itemView.findViewById<Button>(R.id.btn_init_trade)
        var tv_init_amount = itemView.findViewById<TextView>(R.id.tv_init_amount)
        var tv_init_amount_give = itemView.findViewById<TextView>(R.id.tv_init_amount_give)
    }
}
