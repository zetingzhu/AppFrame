package frame.zzt.com.appframe.singlelist;

/**
 * @author: zeting
 * @date: 2020/5/28
 */

/**
 * 展示对话框内容
 */
data class InitTypeDialogData(
        var title: String, // 标题

        var dataList: MutableList<InitTypDataList>,// 具体展示的每一条内容

        var bottomResId: Int,  // 底部展示文案国际化
        var bottomMsg: String,  // 底部展示文案
        var bottomLink: String  // 底部展示文案跳转连接

)

/**
 * 展示对话框里面的某一个条奖励具体内容
 */
data class InitTypDataList(var itemType: Int) {
    companion object {
        // 1 ， 7天会员(标题,描述)
        val ITEM_TYPE_VIP_DAY_7 = 10

        // 通用奖励金(奖励金金额)
        val ITEM_TYPE_CREDIT_GENERAL = 11

        // 产品卷(奖励金金额 , 奖励金名称)
        val ITEM_TYPE_CREDIT_FOR_PRODUCT = 12

        // 充值卷（充值金额，赠送金额，赠送产品）
        val ITEM_TYPE_RECHARGE = 13

        // 交易卷 （交易金额，赠送金额）
        val ITEM_TYPE_TRADE = 14

        // 新手卷 (赠送金额)
        val ITEM_TYPE_CREATE_NOVICE = 15
    }

    var vipTitle: String = ""  // vip 标题
    var vipDesc: String = ""  // vip 描述

    var creditAmount: String = "" // 奖励金金额
    var creditProduct: String = "" //  奖励金名称

    var depositAmount: String = "" // 充值金额
    var giveAmount: String = "" // 赠送金额
    var giveProduct: String = "" // 赠送产品

    var tradeAmount: String = "" // 交易值金额
}


