package com.example.databindsample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.databindsample.databinding.ActSignInBinding
import com.google.gson.Gson


/**
 * @author: zeting
 * @date: 2020/6/19
 *  签到功能页面
 */
class SigningAct : AppCompatActivity() {
    companion object {

        val TAG: String = SigningAct::class.java.simpleName

        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, SigningAct::class.java)
            context.startActivity(intent)
        }

        // 申请日历权限
        var CALENDAR_REQUEST_CODE = 1001

        //  上次显示日历提示
        var LAST_SHOW_PERMISSION_TIME = "showPermissionRemind"

        //推送状态 0开启 1 关闭
        var CALENDAR_STATUS_OPEN = 0
        var CALENDAR_STATUS_CLOSE = 1
    }

    // 绑定
    lateinit var binding: ActSignInBinding

    //  适配器
    var itemAdapter: SigningAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
            一。
        val binding: ActSignInBinding = DataBindingUtil.setContentView(this, R.layout.act_sign_in)
            二。
        binding = ActSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        */

        initView()
        initData()
    }


    private fun initData() {
        var gson = Gson()
        var jsonText = "{\"continuousDay\":0,\"creditAmount\":\"276.8\",\"modelList\":[{\"credit\":\"0.3\",\"day\":\"今天\",\"sort\":1,\"status\":0,\"type\":0},{\"credit\":\"0.3\",\"day\":\"2nd\",\"sort\":2,\"status\":0,\"type\":0},{\"credit\":\"0.6\",\"day\":\"3rd\",\"sort\":3,\"status\":0,\"type\":1},{\"credit\":\"0.3\",\"day\":\"4th\",\"sort\":4,\"status\":0,\"type\":0},{\"credit\":\"0.3\",\"day\":\"5th\",\"sort\":5,\"status\":0,\"type\":0},{\"credit\":\"0.3\",\"day\":\"6th\",\"sort\":6,\"status\":0,\"type\":0},{\"credit\":\"0.9\",\"day\":\"7th\",\"sort\":7,\"status\":0,\"type\":2}],\"status\":3}"
        var dataSing: BindSingingObj = gson.fromJson(jsonText, BindSingingObj::class.java)
        binding.signingObj = dataSing
        dataSing?.modelList?.run {
            itemAdapter?.notifyData(this)
        }
    }


    private fun initView() {
        // 设置grid列表
        var gridLayoutManager = GridLayoutManager(this@SigningAct, 4)
        binding.rvSignInItem.layoutManager = gridLayoutManager
        itemAdapter = SigningAdapter(null)
        val gridSpace = BadgeViewUtil.dp2px(this@SigningAct, 9f) //每个item的宽度
        binding.rvSignInItem.addItemDecoration(RecycleGridSpaceDivider(gridSpace, gridSpace))
        binding.rvSignInItem.adapter = itemAdapter
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (itemAdapter?.mList?.get(position)?.type == BindSingingItemObj.TYPE_SIGN_GOLD) 2 else 1
            }
        }

        // 进入到我的钱包
        binding.tvSignInfo3.text = Html.fromHtml(getString(R.string.s40_10))

    }


}