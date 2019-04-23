package frame.zzt.com.appframe.anim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

/**
 * Created by zeting
 * Date 19/2/20.
 */

public class AnimShowActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mIntent = getIntent() ;
        int startIndex = mIntent.getIntExtra( "index" , 0) ;
        if (startIndex == 5 ){
            setupWindowAnimations() ;
        }

        setContentView(R.layout.activity_anim_show);
        ButterKnife.bind(this);
        initView();

//        Window window = getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

//        StatusBarUtil.setTranslucent(this , 10) ;
        StatusBarUtil.setTransparent(this);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setReturnTransition(slide);

            Explode explode = new Explode();
            slide.setDuration(1000);
            getWindow().setReenterTransition(explode);
        }
    }

    private void initView() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
