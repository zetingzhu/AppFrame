package com.zzt.lurcache;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.kting.cacheutil.MyDiskCacheManager;
import com.kting.cacheutil.MyLruCacheBitmapManager;

import java.util.LinkedHashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView image_view_0, image_view_1, image_view_2, image_view_3, image_view_4, image_view_5, image_view_6, image_view_7;
    //    androidx.collection.LruCache<String, Bitmap> lruCacheX;
    // 磁盘缓存工具
    DiskLruCache diskLruCache;
    private Button btn_click1, btn_click2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        testShowImage();
        testLinkedHashMap();
        testLinkedHashMap1();

//        testShowDiskCashTest();
    }

    private void initView() {
        btn_click1 = findViewById(R.id.btn_click);
        btn_click2 = findViewById(R.id.btn_click2);
        btn_click1.setText("跳到文件缓存");
        btn_click2.setVisibility(View.GONE);
        btn_click1.setOnClickListener(this);
        btn_click2.setOnClickListener(this);
    }

    private void testShowDiskCashTest() {
        User user = new User();
        user.name = "11111111";
        user.age = "2222222222";

        String key = "key_test_1" + System.currentTimeMillis();
        boolean boo = MyDiskCacheManager.getInstance(this).put(key, user);
        Log.d("TAG", "是否存储成功：：" + boo + " - Key:" + key);

        User uu = MyDiskCacheManager.getInstance(this).get(key);
        if (uu != null) {
            Log.d("TAG", "这个地方返回值：" + uu.toString());
        }


        image_view_0 = findViewById(R.id.image_view_0);

        int drawableRid = R.drawable.icon_0;
        String icon_key = "key_test_1";
        Bitmap thisBitmap = MyLruCacheBitmapManager.getInstance().get(icon_key);
        if (thisBitmap == null) {
            Log.d(TAG, "判断查找的图片是不是为空 重新创建");
            thisBitmap = BitmapFactory.decodeResource(getResources(), drawableRid);
            MyLruCacheBitmapManager.getInstance().put(icon_key, thisBitmap);
        } else {
            Log.d(TAG, "判断查找的图片是不是为空 已经存在");
        }
        image_view_0.setImageBitmap(thisBitmap);

        Bitmap thisBitma2 = MyLruCacheBitmapManager.getInstance().get(icon_key);
        if (thisBitma2 != null) {
            Log.d(TAG, "判断查找的图片是不是为空 2 已经存在");
            image_view_2.setImageBitmap(thisBitma2);
        }
    }

    public void testShowImage() {
        image_view_1 = findViewById(R.id.image_view_1);
        image_view_2 = findViewById(R.id.image_view_2);
        image_view_3 = findViewById(R.id.image_view_3);
        image_view_4 = findViewById(R.id.image_view_4);
        image_view_5 = findViewById(R.id.image_view_5);
        image_view_6 = findViewById(R.id.image_view_6);
        image_view_7 = findViewById(R.id.image_view_7);

//        android.util.LruCache lruCache = new android.util.LruCache(10);
//        lruCacheX = new androidx.collection.LruCache<String, Bitmap>(3);

        setImage(image_view_1, "icon_1", R.drawable.icon_1);
        printMapLog(MyLruCacheBitmapManager.getInstance().snapshot(), "图片1");
        setImage(image_view_2, "icon_2", R.drawable.icon_2);
        setImage(image_view_3, "icon_3", R.drawable.icon_3);
        setImage(image_view_4, "icon_4", R.drawable.icon_4);
        setImage(image_view_5, "icon_5", R.drawable.icon_5);
        printMapLog(MyLruCacheBitmapManager.getInstance().snapshot(), "图片5");
        setImage(image_view_6, "icon_1", R.drawable.icon_1);
        printMapLog(MyLruCacheBitmapManager.getInstance().snapshot(), "图片6");
        setImage(image_view_7, "icon_5", R.drawable.icon_5);
        printMapLog(MyLruCacheBitmapManager.getInstance().snapshot(), "图片7");

    }


    public void setImage(ImageView img, String key, int drawableRid) {
        Bitmap thatBitmap = MyLruCacheBitmapManager.getInstance().get(key);
        if (thatBitmap == null) {
            thatBitmap = BitmapFactory.decodeResource(getResources(), drawableRid);
            System.out.println("打印结果 图片：" + thatBitmap.toString());
            MyLruCacheBitmapManager.getInstance().put(key, thatBitmap);
            System.out.println("打印结果 大小：" + thatBitmap.getByteCount());
        } else {
            System.out.println("打印结果 这一次没有保存：" + key);
        }
        img.setImageBitmap(thatBitmap);
    }

    public void printMapLog(Map<String, Bitmap> map, String log) {
        for (Map.Entry<String, Bitmap> entry : map.entrySet()) {
            System.out.println("打印结果 " + log + " :" + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("\n\t");
    }

    public void printMapLog(LinkedHashMap<Integer, Integer> map, String log) {
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("打印结果 " + log + " :" + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("\n\t");
    }

    /**
     * accessOrder  排序方式 - true 用于*访问顺序，  false 用于插入顺序
     */
    public void testLinkedHashMap() {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, true);
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
        map.put(7, 7);
        map.get(1);
        printMapLog(map, "1");
        map.get(5);
        printMapLog(map, "2");
        map.put(8, 8);
        map.put(9, 9);
        map.put(10, 10);
        printMapLog(map, "3");
    }

    public void testLinkedHashMap1() {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, false);
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
        map.put(7, 7);
        map.get(1);
        printMapLog(map, "11");
        map.get(5);
        printMapLog(map, "12");
        map.put(8, 8);
        map.put(9, 9);
        map.put(10, 10);
        printMapLog(map, "13");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_click) {
            startActivity(new Intent(MainActivity.this, ImgListAct.class));
        }
    }

    public void onBtnClick(View view) {
    }
}
