package frame.zzt.com.appframe.singlelist;

import android.app.Activity
import android.content.DialogInterface
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import frame.zzt.com.appframe.R

/**
 * @author: zeting
 * @date: 2020/5/28
 * 初始化启动请求弹框工具类
 */
class InitDialogUtil private constructor() {

    companion object {
        // vip 运营操作弹窗
        val TYPE_REQUEST_VIP_OPERATIONS = 0

        // vip召回
        val TYPE_REQUEST_VIP_RECALL = 1

        // 10 天老用户召回
        val TYPE_REQUEST_OLD_USER_RECALL_15 = 2

        // 15 天老用户召回
        val TYPE_REQUEST_OLD_USER_RECALL_10 = 3


        val instance: InitDialogUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            InitDialogUtil()
        }
    }


    /**
     * 根据不同的类型来请求不同的接口，展示不同的对话框效果
     */
    fun requestInitDialogType(baseActivity: Activity, requestType: Int, callBack: Handler.Callback?, mRecyclerView: androidx.recyclerview.widget.RecyclerView) {
        var request = mapOf(Pair("111111111111111111111111111111111111111111111", requestType?.toString()))
//        HttpUtils.post(AndroidAPIConfig.   , request, object : HttpCallback< >(){}  )

        showTypeDialog(baseActivity, getTestData(), mRecyclerView)
    }


    /**
     * 显示dialog
     */
    fun showTypeDialog(baseActivity: Activity, typeData: InitTypeDialogData, mRecyclerView: androidx.recyclerview.widget.RecyclerView) {

//        var dialog: InitListDialog = InitListDialog(baseActivity)
//        var mRecyclerView = dialog.getDialogRecyclerview()


        var adapter: InitDialogAdapter = InitDialogAdapter()

        mRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(baseActivity)

        // 添加头
        val header = LayoutInflater.from(baseActivity).inflate(R.layout.init_dialog_header_layout, mRecyclerView, false)
        var tv_init_header = header.findViewById<TextView>(R.id.tv_init_header)
        tv_init_header.setText(typeData.title)
        adapter.addHeaderView(header)

        // 添加脚
        val bottom = LayoutInflater.from(baseActivity).inflate(R.layout.init_dialog_bottom_layout, mRecyclerView, false)
        var tv_init_bottom = bottom.findViewById<TextView>(R.id.tv_init_bottom)
        tv_init_bottom.setText(typeData.bottomMsg)
        adapter.addBottomView(bottom)

        // 设置适配器
        mRecyclerView.adapter = adapter

        // 添加数据，刷新适配器
        adapter.setData(typeData.dataList)

//        dialog.setBigTitleMarginTop(11)
//        dialog.setRightDeleteClick(null, object : InitListDialog.OnClickListener {
//            override fun onClick(dialog: DialogInterface?, v: View?) {
//                dialog?.dismiss()
//            }
//        })
//        dialog.show()
    }


    /**
     * 测试数据
     * 获取测试数据
     */
    fun getTestData(): InitTypeDialogData {

        var itemList = mutableListOf<InitTypDataList>()
        var item1 = InitTypDataList(InitTypDataList.ITEM_TYPE_VIP_DAY_7)
        item1.vipTitle = "这是vip标题"
        item1.vipDesc = "这是vip描述信息"
        itemList.add(item1)

        var item2 = InitTypDataList(InitTypDataList.ITEM_TYPE_CREDIT_GENERAL)
        item2.creditAmount = "2222.222"
        itemList.add(item2)

        var item3 = InitTypDataList(InitTypDataList.ITEM_TYPE_CREDIT_FOR_PRODUCT)
        item3.creditAmount = "333.33"
        item3.creditProduct = "USOIL"
        itemList.add(item3)

        var item4 = InitTypDataList(InitTypDataList.ITEM_TYPE_RECHARGE)
        item4.depositAmount = "200"
        item4.giveAmount = "100"
        item4.giveProduct = "GBPUSD"
        itemList.add(item4)

        var item5 = InitTypDataList(InitTypDataList.ITEM_TYPE_TRADE)
        item5.tradeAmount = "300"
        item4.giveAmount = "20"
        itemList.add(item5)

        var item6 = InitTypDataList(InitTypDataList.ITEM_TYPE_CREATE_NOVICE)
        item4.giveAmount = "$100"
        itemList.add(item6)

        var data: InitTypeDialogData = InitTypeDialogData("你已收获一份vip大礼包",
                itemList,
                R.string.show_item_drag, "这个拼接文案可以点击", "这个地方就是点击连接地址"
        )

        return data
    }


}