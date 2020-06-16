package frame.zzt.com.appframe.kotlin

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import frame.zzt.com.appframe.R

import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * kotlin 创建的类
 */
class ActivityKotlin : AppCompatActivity() , View.OnClickListener {
    var TAG:String = "ActivityKotlin"

    var btn01:String = "第一个按钮"
    var btn02  = "第二个按钮"

    var otherName: String? = null

    override fun onClick(v: View?) {
        Log.d(TAG , "点击了哪一个按钮：" + v?.id )
        when(v!!.id){
            R.id.btn_kotlin_01 -> {
                Log.d(TAG , "点击了第一个按钮")
                showToast(this, "显示信息")
            }
            R.id.btn_kotlin_02 -> {
                Log.d(TAG , "点击了第二个按钮")
                btn02 = "点击后改变"
                btn_kotlin_02.setText(btn02)
                this.toast("显示第二条信息")
            }
            R.id.btn_kotlin_03-> {
                Log.d(TAG , "点击了第二个按钮")
                btn02 = "点击后改变"
                btn_kotlin_02.setText(btn02)
                this.toast("显示第二条信息")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)


        initView()
    }

    private fun initView() {
        btn_kotlin_01.setText(btn01)
        btn_kotlin_01.setOnClickListener(this)
        btn_kotlin_02.setText(btn02)
        btn_kotlin_02.setOnClickListener(this)


    }


    fun whenTest(x: Int){
        when (x) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    /*
    * show toast in activity
    * */
    fun Activity.toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}
