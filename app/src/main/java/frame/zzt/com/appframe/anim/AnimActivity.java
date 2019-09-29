package frame.zzt.com.appframe.anim;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;
import frame.zzt.com.appframe.mvp.mvpbase.BaseActivity;

/**
 * Created by zeting
 * Date 19/2/20.
 */

public class AnimActivity extends BaseAppCompatActivity {

    private static final String TAG = AnimActivity.class.getSimpleName() ;


    @BindView(R.id.tv_anim_msg)
    TextView tv_anim_msg ;

    @BindView(R.id.btn_anim_start)
    Button btn_anim_start ;

    @BindView(R.id.iv_yx)
    ImageView iv_yx ;

    @BindView(R.id.spinner_anim)
    Spinner spinner_anim ;

    @BindView(R.id.btn_01)
    Button btn_01 ;
    @BindView(R.id.btn_02)
    Button btn_02 ;
    @BindView(R.id.btn_03)
    Button btn_03 ;

    // 选择的数据id
    private int selectPosition = 0 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anim_start);
        ButterKnife.bind(this);
        initView();
        initBar();
    }

    private void initBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 4.0 添加
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            // 5.0 添加
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //注意要清除 FLAG_TRANSLUCENT_STATUS flag
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light));
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//         6.0
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            注意要清除 FLAG_TRANSLUCENT_STATUS flag
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 6.0 设置状态栏颜色为深色
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }



        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = getWindow();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
            }
        });
        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = getWindow();
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(Color.BLACK);
                }
            }
        });
        btn_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

   }

//    public  void showBar(Activity activity){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//            int count = decorView.getChildCount();
//            if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
//                decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//            } else {
//                StatusBarView statusView = createStatusBarView(activity, color, statusBarAlpha);
//                decorView.addView(statusView);
//            }
//
//            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//            rootView.setFitsSystemWindows(true);
//            rootView.setClipToPadding(true);
//            setRootView(activity);
//        }
//    }


            private void initView() {
        btn_anim_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        iv_yx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( selectPosition == 0 ){
                    /**
                     * 和overridePendingTransition类似,设置跳转时候的进入动画和退出动画
                     */
                    ActivityOptionsCompat compat1 = ActivityOptionsCompat.makeCustomAnimation(AnimActivity.this,
                            R.anim.slide_right_in, R.anim.slide_left_out);
                    ActivityCompat.startActivity(AnimActivity.this, new Intent(AnimActivity.this,  AnimShowActivity.class), compat1.toBundle());
                }else if ( selectPosition == 1 ){
                    /**
                     * 通过把要进入的Activity通过放大的效果过渡进去
                     * 举一个简单的例子来理解source=view,startX=view.getWidth(),startY=view.getHeight(),startWidth=0,startHeight=0
                     * 表明新的Activity从view的中心从无到有慢慢放大的过程
                     */
                    ActivityOptionsCompat compat2 = ActivityOptionsCompat.makeScaleUpAnimation(v,
                            v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(AnimActivity.this, new Intent(AnimActivity.this,  AnimShowActivity.class), compat2.toBundle());
                }else if ( selectPosition == 2 ){
                    /**
                     * 通过放大一个图片过渡到新的Activity
                     */
                    v.setDrawingCacheEnabled(true);
                    Bitmap bitmap=v.getDrawingCache();
                    ActivityOptionsCompat compat3 = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(v,bitmap, 0, 0);
                    ActivityCompat.startActivity(AnimActivity.this, new Intent(AnimActivity.this,  AnimShowActivity.class), compat3.toBundle());
                }else if ( selectPosition == 3 ){
                    /**
                     * 场景动画，体现在两个Activity中的某些view协同去完成过渡动画效果
                     */
                    ActivityOptionsCompat compat4 =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(AnimActivity.this,  v , getString(R.string.show_scene_transition_anim));
                    ActivityCompat.startActivity(AnimActivity.this, new Intent(AnimActivity.this,  AnimShowActivity.class), compat4.toBundle());
                }else if ( selectPosition == 4 ){
                    /**
                     * 场景动画，同上是对多个View同时起作用
                     */

                    Pair<View, String> imagePair = Pair.create(findViewById(R.id.iv_yx), getString(R.string.show_scene_transition_anim));
                    Pair<View, String> textPair = Pair.create((View) tv_anim_msg, getString(R.string.show_scene_transition_anim_title));

                    ActivityOptionsCompat compat5 =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(AnimActivity.this, imagePair,textPair );
                    ActivityCompat.startActivity(AnimActivity.this, new Intent(AnimActivity.this,  AnimShowActivity.class), compat5.toBundle());
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Intent mIntent = new Intent();
                        mIntent.setClass(AnimActivity.this , AnimShowActivity.class ) ;
                        mIntent.putExtra( "index" , selectPosition );
                        startActivity(mIntent , ActivityOptions.makeSceneTransitionAnimation(AnimActivity.this).toBundle());
                    }
                }
            }
        });

        ArrayList<String> list = new ArrayList<String>();
        list.add("1 - makeCustomAnimation");
        list.add("2 - makeScaleUpAnimation");
        list.add("3 - makeThumbnailScaleUpAnimation");
        list.add("4 - makeSceneTransitionAnimation");
        list.add("5 - makeSceneTransitionAnimation");
        list.add("6 - 自己转换动画");
        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.item_show_anim, R.id.tv_item_show,list);
        spinner_anim.setAdapter(adapter2);
        spinner_anim.setOnItemSelectedListener(new spinnerListener());







    }

    class spinnerListener implements android.widget.AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG , "选择数据" + position );
            selectPosition = position ;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG , "onNothingSelected" );
        }
    }

}
