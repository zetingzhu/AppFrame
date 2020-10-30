package com.zzt.lurcache;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kting.cacheutil.MyDiskCacheManager;

public class ImgListAct extends AppCompatActivity {

    private static final String TAG = ImgListAct.class.getSimpleName();

    private ImageView image_view_0, image_view_1, image_view_2, image_view_3, image_view_4, image_view_5, image_view_6, image_view_7;
    private Button btn_click1, btn_click2, btn_click3;


    int drawableRid = R.drawable.icon_0;
    String icon_key = "key_test_1";

    int drawableRid3 = R.drawable.icon_3;
    String icon_key3 = "key_test_3";

    int drawableRid4 = R.drawable.icon_4;
    String icon_key4 = "key_test_4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_view_0 = findViewById(R.id.image_view_0);
        image_view_1 = findViewById(R.id.image_view_1);
        image_view_2 = findViewById(R.id.image_view_2);
        image_view_3 = findViewById(R.id.image_view_3);
        image_view_4 = findViewById(R.id.image_view_4);
        image_view_5 = findViewById(R.id.image_view_5);
        image_view_6 = findViewById(R.id.image_view_6);
        image_view_7 = findViewById(R.id.image_view_7);
        btn_click1 = findViewById(R.id.btn_click);
        btn_click2 = findViewById(R.id.btn_click2);
        btn_click3 = findViewById(R.id.btn_click3);


        showImgList();

    }

    private void addDiskCache(String icon_key, int drawableRid) {
//        int drawableRid = R.drawable.icon_0;
//        String icon_key = "key_test_1";
        Bitmap thisBitmap = MyDiskCacheManager.getInstance(ImgListAct.this).getBitmap(icon_key);
        if (thisBitmap == null) {
            Log.d(TAG, "判断查找的图片是不是为空 添加进入缓存");
            thisBitmap = BitmapFactory.decodeResource(getResources(), drawableRid);
            boolean boo = MyDiskCacheManager.getInstance(ImgListAct.this).putBitmap(icon_key, thisBitmap);
            Log.d("TAG", "是否存储成功：：" + boo + " - Key:" + icon_key);
        } else {
            Log.d(TAG, "判断查找的图片是不是为空 已经存在");
        }
    }

    private void showImgList() {
        showImageImg(image_view_0, icon_key);
        showImageImg(image_view_2, icon_key);
        showImageImg(image_view_3, icon_key3);
        showImageImg(image_view_4, icon_key4);
    }

    private void showImageImg(ImageView iv, String icon_key) {
        Bitmap thisBitmap = MyDiskCacheManager.getInstance(ImgListAct.this).getBitmap(icon_key);
        if (thisBitmap != null) {
            Log.d(TAG, "判断查找的图片是不是为空 " + icon_key + " 已经存在");
            iv.setImageBitmap(thisBitmap);
        } else {
            Log.d(TAG, "判断查找的图片是不是为空 " + icon_key + " 没有查找到图片");
        }
    }


    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                showImgList();
                break;
            case R.id.btn_click2:
                addDiskCache(icon_key, drawableRid);
                break;
            case R.id.btn_click3:
                addDiskCache(icon_key3, drawableRid3);
                break;
            case R.id.btn_click4:
                addDiskCache(icon_key4, drawableRid4);
                break;
        }
    }
}