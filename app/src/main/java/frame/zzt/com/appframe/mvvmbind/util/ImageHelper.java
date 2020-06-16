package frame.zzt.com.appframe.mvvmbind.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import frame.zzt.com.appframe.R;

public class ImageHelper {

    /**
     * public static [方法名] 地声明成@BindingAdapter  可以生产自定义数据
     * 1.加载图片,无需手动调用此方法
     * 2.使用@BindingAdapter注解设置自定义属性的名称，imageUrl就是属性的名称，
     * 当ImageView中使用imageUrl属性时，会自动调用loadImage方法，
     *
     * @param imageView ImageView
     * @param url       图片地址
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }


}
