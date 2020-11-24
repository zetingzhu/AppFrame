package com.mykotiln.activity

import android.os.Bundle
import android.view.View
import com.mykotiln.R
import com.mykotiln.util.MyRecycleListItem
import com.mykotiln.util.MyRecyclerViewKotlin
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * kotlin Activity 的创建
 */
class ActivityKotlin : BaseActivity(), View.OnClickListener {

    var mPer = KotilnPersenter(this)
    var rv_kotlin_list: MyRecyclerViewKotlin? = null
    var returnInt: Int = 0 // 返回的值

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_item_01 -> {
                mPer.mainStr()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        initView()
    }

    fun initView() {
        rv_kotlin_list = findViewById<MyRecyclerViewKotlin>(R.id.rv_kotlin_list)
        returnInt = mPer.setItemOperateKotlin(rv_kotlin_list!!)

        tv_item_01.setOnClickListener(this@ActivityKotlin)
        tv_item_01.setText("mainStr")
        tv_item_02.setOnClickListener(View.OnClickListener {
            mPer.mainQJ(arrayOf("1", "2", "3"))
        })
        tv_item_03.setText("mainQJ")
        tv_item_03.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                setInterFace(object : TestInterFace {
                    override fun test() {
                        println("匿名内部类")
                    }
                })
            }
        })

        var onClickLin: View.OnClickListener = View.OnClickListener {

        }

    }


    fun setInterFace(test: TestInterFace) {
        test.test()
    }

    /**
     * 定义接口
     */
    interface TestInterFace {
        fun test()
    }

}
