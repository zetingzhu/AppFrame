package frame.zzt.com.appframe.guide

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import frame.zzt.com.appframe.R
import frame.zzt.com.appframe.dialog.DialogWrapper
import kotlinx.android.synthetic.main.activity_guide_layout.*

/**
 * @author: zeting
 * @date: 2020/7/22
 * 引导蒙版页面
 */
class ActivityGuide : AppCompatActivity(), View.OnClickListener {
    companion object {
        var TAG = ActivityGuide::javaClass.name
    }

    var guideCustomView: GuideCustomView? = null
    var images: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_layout)

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        images = ImageView(this@ActivityGuide)
        images?.setImageResource(R.drawable.icon_child2)
        images?.layoutParams = params

        btn_guide_1.setOnClickListener {
            guideCustomView = GuideCustomView.Builder
                    .newInstance(this@ActivityGuide)
                    .setTargetView(it) //设置目标
                    .setCustomGuideView(images) //设置蒙层上面使用的图片
                    .setDirction(GuideCustomView.Direction.BOTTOM)
                    .setShape(GuideCustomView.MyShape.CIRCULAR) // 设置圆形显示区域，
                    .setBgColor(Color.parseColor("#222222"))
                    .setOnclickListener { guideCustomView?.hide() }
                    .build()
            guideCustomView?.show()
        }
        btn_guide_2.setOnClickListener {
            guideCustomView = GuideCustomView.Builder
                    .newInstance(this@ActivityGuide)
                    .setTargetView(it) //设置目标
                    .setCustomGuideView(images) //设置蒙层上面使用的图片
                    .setDirction(GuideCustomView.Direction.LEFT_BOTTOM)
                    .setShape(GuideCustomView.MyShape.ELLIPSE)
                    .setBgColor(Color.parseColor("#222222"))
                    .setOnclickListener { guideCustomView?.hide() }
                    .build()
            guideCustomView?.show()
        }
        btn_guide_3.setOnClickListener {
            guideCustomView = GuideCustomView.Builder
                    .newInstance(this@ActivityGuide)
                    .setTargetView(it) //设置目标
                    .setCustomGuideView(images) //设置蒙层上面使用的图片
                    .setDirction(GuideCustomView.Direction.RIGHT_BOTTOM)
                    .setShape(GuideCustomView.MyShape.RECTANGULAR)
                    .setBgColor(Color.parseColor("#222222"))
                    .setOnclickListener { guideCustomView?.hide() }
                    .build()
            guideCustomView?.show()
        }
        btn_guide_4.setOnClickListener {
            guideCustomView = GuideCustomView.Builder
                    .newInstance(this@ActivityGuide)
                    .setTargetView(it) //设置目标
                    .setCustomGuideView(images) //设置蒙层上面使用的图片
                    .setDirction(GuideCustomView.Direction.LEFT)
                    .setShape(GuideCustomView.MyShape.RECTANGULAR)
                    .setRadius(100)
                    .setOffset(0, 0)
                    .setBgColor(Color.parseColor("#222222"))
                    .setOnclickListener { guideCustomView?.hide() }
                    .build()
            guideCustomView?.show()
        }
        var dialog: Dialog? = null
        btn_guide_5.text = "弹出对话框"
        btn_guide_5.setOnClickListener {
            dialog = DialogWrapper.Builder(this@ActivityGuide)
                    // 添加图片
                    .appendImageView(ContextCompat.getDrawable(this@ActivityGuide, R.drawable.icon_privacy)!!)
                    // 添加文本
                    .appendTextView("这是个测试按钮", 24, ContextCompat.getColor(this@ActivityGuide, R.color.black))
                    // 添加一个系统的View
                    .appendAddView(object : DialogWrapper.AddSysViewListener<Button> {
                        override fun getAddView(): Button {
                            var btn = Button(this@ActivityGuide)
                            btn.setText("这个是系统的View")
                            return btn
                        }
                    })
                    .setBottomButton("确定", 24, ContextCompat.getColor(this@ActivityGuide, R.color.black),
                            ContextCompat.getColor(this@ActivityGuide, R.color.navajowhite), 10) { d, v ->
                        Log.d(TAG, "这个按钮有没有被点击")
                        guideCustomView?.isShown?.run {
                            if (this) {
                                Log.d(TAG, " 已经显示出来了")
                                return@setBottomButton
                            }
                        }
                        guideCustomView = GuideCustomView.Builder
                                .newInstance(v.context)
                                .setDialog(dialog)
                                .setTargetView(v) //设置目标
                                .setCustomGuideView(images) //设置蒙层上面使用的图片
                                .setDirction(GuideCustomView.Direction.BOTTOM)
                                .setShape(GuideCustomView.MyShape.RECTANGULAR)
                                .setBgColor(Color.parseColor("#222222"))
                                .setOnclickListener {
                                    Log.d(TAG, " 点击了隐藏起来")
                                    guideCustomView?.hide()
                                }
                                .build()
                        guideCustomView?.show()
                    }
                    .setBottomButtonMar(16, 16, 16, 16)
                    .setCancelable(true)
                    .show()
        }
        btn_guide_6.setOnClickListener {
        }
        btn_guide_7.setOnClickListener {

        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}