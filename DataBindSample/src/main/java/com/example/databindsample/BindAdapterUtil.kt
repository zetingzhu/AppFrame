package com.example.databindsample

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion


/**
 * @author: zeting
 * @date: 2020/6/19
 * UI绑定适配
 */
object BindAdapterUtil {

    /**To use data binding annotations in Kotlin, apply the 'kotlin-kapt' plugin in your module's build.gradle
     * 设置字体颜色，传入的值为颜色资源id
     * 使用：
     * android:textColor="@{@color/white}"
     * 适用于：
     *  TextView 直接设置字体颜色 resource Id
     */
    @BindingAdapter("android:textColor")
    @JvmStatic
    fun setTextViewColor(textView: TextView, resource: Int) {
        textView.setTextColor(ContextCompat.getColor(textView.context, resource))
    }

    /**
     * 使用：
     * android:visibility="@{true}"
     * 适用于：给view 设置隐藏显示属性
     */
    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setViewVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * 设置imageview 的图片资源
     * 使用：
     * android:src="@{ @drawable/app_icon}"
     * 适用于：同 setImageUrl 方法
     * ImageView 设置图片 resource 或者是设置图片url
     */
    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    /**
     * 设置 imagview 资源 ，图片资源id或者是url
     * 使用：
     * app:imageSrc="@{ @drawable/app_icon}" 或者 app:imageUrl="http://xxx.xxx.xxx""
     * 适用于：
     * ImageView 设置图片 resource 或者是设置图片url
     */
    @BindingAdapter(value = ["imageUrl", "imageSrc", "headUrl"], requireAll = false)
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?, imageSrc: Int?, headUrl: String?) {
        when {
            imageSrc != null -> {
                imageView.setImageResource(imageSrc)
            }
            url != null -> {
                // 网络加载图片
//                Glide.with(imageView.context).load(url).into(imageView)
            }
            headUrl != null -> {
                // 加载圆形头像
//                Glide.with(imageView.context).load(headUrl).transform(CircleCrop()).into(imageView)
            }
        }
    }

    /**
     * 按钮设置设置背景颜色
     * 使用：
     *    android:background="@{ @drawable/app_icon}"
     * 适用于:
     *   给所有的view 设置背景图片，引用直接传入 resource Id
     */
    @BindingAdapter("android:background")
    @JvmStatic
    fun setImageViewResource(view: View, resource: Int) {
        view.setBackgroundResource(resource)
    }


    /**
     * int 都应转换为 Drawable
     * 使用：
     * android:background="@{@color/white}"
     * 适用于:
     * 背景图片设置颜色，，引用直接传入 color resource Id
     */
    @BindingConversion
    @JvmStatic
    fun convertColorToDrawable(color: Int): ColorDrawable {
        return ColorDrawable(color)
    }


    /**
     * 设置view 的tint颜色
     * 使用:
     *   app:backgroundTint="@{}"
     *   适用于，给view代码设置tint
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @BindingAdapter("backgroundTint")
    @JvmStatic
    fun setBackgroundTint(view: View, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundTintList(view.context.resources.getColorStateList(color))
        }
    }

    /**
     * 设置TintTextView 的tint颜色
     * 使用:
     *   app:backgroundTintColor="@{}"
     *   适用于：
     *   给 TintTextView 代码设置tint颜色
     */
    @BindingAdapter("backgroundTintColor")
    @JvmStatic
    fun setBackgroundTint(view:  TextView, color: Int) {
//        view.setBackgroundTintList(color)
    }


    @BindingConversion
    @JvmStatic
    fun convertColorToTintList(color: Int): ColorStateList {
        return ColorStateList.valueOf(color)
    }

}
