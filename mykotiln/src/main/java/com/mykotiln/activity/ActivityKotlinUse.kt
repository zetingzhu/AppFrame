package com.mykotiln.activity

import android.os.Bundle
import com.mykotiln.R
import com.mykotiln.util.MyRecyclerViewKotlin

/**
 * kotlin Activity 的创建
 */
class ActivityKotlinClass : BaseActivity()  {

    var mPer = KotilnClassPersenter(this)
    var rv_kotlin_list : MyRecyclerViewKotlin?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        initView()
    }


    fun initView(){
        rv_kotlin_list = findViewById(R.id.rv_kotlin_list) as MyRecyclerViewKotlin
        mPer.setItemKotlinClass(rv_kotlin_list!!)

    }

}
