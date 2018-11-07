package com.mykotiln.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mykotiln.R
import kotlinx.android.synthetic.main.activity_kotlin.*


class KotlinActivity : AppCompatActivity() , View.OnClickListener {

    var mPer = KotilnPersenter(this)

    override fun onClick(v: View?) {
        when(v!!.id){
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

    fun initView(){
        tv_item_01.setOnClickListener (this@KotlinActivity)
        tv_item_01.setText("mainStr")
        tv_item_02.setOnClickListener( View.OnClickListener {
            mPer.mainQJ(arrayOf("1","2","3"))
        })
        tv_item_03.setText("mainQJ")
        tv_item_03.setOnClickListener ( object : View.OnClickListener{
            override fun onClick(v: View?) {

                setInterFace(object : TestInterFace{
                    override fun test() {
                        println("匿名内部类")
                    }
                })
            }
        })

        var onClickLin : View.OnClickListener = View.OnClickListener {

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
