package com.zzt.myviewpager.anim;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author: zeting
 * @date: 2020/1/16
 * 旋转平移动画
 */
public class RotationPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
//        float absPos = Math.abs(position);
        float rotation = position * 360;
        page.setRotation(rotation);

//        float translationY = absPos * 500f;
//        page.setTranslationX(translationY);
//        float translationX = absPos * 350f;
//        page.setTranslationX(translationX);

//        float scale = absPos > 1 ? 0F : 1 - absPos;
//        page.setScaleX(scale);
//        page.setScaleY(scale);
    }
}
