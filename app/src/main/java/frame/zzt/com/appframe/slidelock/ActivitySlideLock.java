package frame.zzt.com.appframe.slidelock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;
import frame.zzt.com.appframe.Util.DisplayUtils;
import frame.zzt.com.appframe.Util.SystemUtil;
import frame.zzt.com.appframe.widget.MyScrollView;

/**
 *  下滑开关电门动画
 * Created by zeting
 * Date 18/12/10.
 */

public class ActivitySlideLock extends Activity {
    private static final String TAG = ActivitySlideLock.class.getSimpleName() ;
    SlideLockVerticalView slvv_touch ;
    int turnon = 0;// 0 开 ，1 关

    private LinearLayout ll_anim_01 , ll_anim_02 , ll_anim_03;
    private int ll02Height1 , ll02Width ;
    private int ll02Height2 ;
    private int ll02Height3 ;
    private int ll02Height4 ;
    private int ll02Height5 ;

    @BindView(R.id.btn_start)
    public Button btn_start ;
    @BindView( R.id.btn_close)
    public Button btn_close ;
    @BindView( R.id.scrollView)
    public MyScrollView scrollView ;

    private ValueAnimator openAnimator ;// 展开动画
    private ScaleAnimation scaleAnimator ;// 缩放动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏系统虚拟按键
        SystemUtil.hideBottomUIMenu(this);

        setContentView(R.layout.activity_slide_lock);
        ButterKnife.bind(this);
        initView();
        initLayoutView();


//        SystemUtil.hideNavigationBar(this);
    }

    private void initLayoutView() {
        ll_anim_01 = (LinearLayout) findViewById(R.id.ll_anim_01);
        ll_anim_02 = (LinearLayout) findViewById(R.id.ll_anim_02);
        ll_anim_03 = (LinearLayout) findViewById(R.id.ll_anim_03);

        ll_anim_02.setVisibility(View.GONE);
        ll_anim_03.setVisibility(View.GONE);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAnim( ll_anim_02 ,  ll_anim_03 ,  ll02Height4 , 2000 );
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clostAnim(ll_anim_02 ,  ll_anim_03 ) ;
            }
        });

        // 测量视图
        ViewTreeObserver vto = ll_anim_02.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll02Height1 = ll_anim_02.getHeight();
                ll_anim_02.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.d(TAG , " - ViewTreeObserver OnGlobalLayoutListener 高：" + ll02Height1 ) ;
            }
        });


        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                ll02Height2 = ll_anim_02.getMeasuredHeight();
                ll_anim_02.getViewTreeObserver().removeOnPreDrawListener(this);
                Log.d(TAG , " - ViewTreeObserver OnPreDrawListener 高：" + ll02Height2 ) ;
                return true;
            }
        });

        /**
         它有三种模式：
         ①、UNSPECIFIED(未指定)，父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小；
         ②、EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
         ③、AT_MOST(至多)，子元素至多达到指定大小的值。
         */
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ll_anim_02.measure(w, h);
        ll02Height3 = ll_anim_02. getMeasuredHeight() ;
        Log.d(TAG , " - MeasureSpec  高：" + ll02Height3 ) ;


        ll02Height4 = DisplayUtils.convertDipOrPx(this , 50);
        Log.d(TAG , " - dp  高：" + ll02Height4 ) ;
        ll02Height5 = DisplayUtils.dp2px(this , 50);
        Log.d(TAG , " - dp  高：" + ll02Height5 ) ;


        scrollView.setScanScrollChangedListener(new MyScrollView.IScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                Log.d(TAG , " - scrollView  滑动到底部："  ) ;
            }

            @Override
            public void onScrolledToTop() {
                Log.d(TAG , " - scrollView  滑动到顶部："  ) ;
            }
        });

    }


    /**
     * 显示动画
     */
    public void showLL1(){


    }

    /**
     * 关闭动画
     */
    private void clostAnim(View vGroup , View vChild){
        if (openAnimator!=null) {
            openAnimator.end();
            vGroup.setVisibility(View.VISIBLE);
        }
        if (scaleAnimator!=null) {
            vChild.clearAnimation();
            vChild.setVisibility(View.VISIBLE);
        }
    }

    /**
     *  开始动画
     * @param vGroup
     * @param vChild
     * @param mHeight
     */
    private void openAnim(View vGroup , final View vChild , int mHeight , final long duration) {
        vGroup.setVisibility(View.VISIBLE);
        vChild.setVisibility(View.GONE);
        openAnimator = createDropAnimator(vGroup, 0, mHeight);
        openAnimator.setDuration(duration) ;
        openAnimator.start();
        openAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                createScaleAnimator( vChild , duration);
            }
        });
    }

    /**
     * 显示展开动画
     * @param v
     * @param start
     * @param end
     * @return
     */
    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /**
     *  显示缩放动画
     * @param v
     */
    private void createScaleAnimator( View v , long duration) {
        v.setVisibility(View.VISIBLE);
        scaleAnimator = new ScaleAnimation(0 ,1,0 ,1, Animation.RELATIVE_TO_SELF ,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimator.setDuration(duration);
        scaleAnimator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(scaleAnimator);
    }

    public void HeidLL1(){
        ll_anim_02.setVisibility(View.GONE);
    }

    private void initView() {
        slvv_touch = (SlideLockVerticalView) findViewById(R.id.slideLockVerticalView);

        if (turnon == 0) { //已启动
            // 设置已启动状态
            slvv_touch.setOpenStatus();
        } else {//未启动
            // 设置状态为未启动
            slvv_touch.setCloseStatus();
        }
        slvv_touch.setOnTouchBring(new SlideLockVerticalView.OnTouchBring() {
            @Override
            public void bringToFront() {
                // 开始时候
                Log.d(TAG , " bringToFront 开始时候" );
            }

            @Override
            public void bringToBack() {
                // 结束时候
                Log.d(TAG , " bringToBack 结束时候" );
            }
        });
        slvv_touch.setOnClickListener(new SlideLockVerticalView.SlideOnClickListener() {
            @Override
            public void onClick() {
                // 只有关闭时候执行动画
                Log.d(TAG , " setOnClickListener 开始动画" );
                // 启动电门动画中
                slvv_touch.startAnimStatus();
                // 显示
                showLL1();
            }

            @Override
            public void onMoveClick() {
                Log.d(TAG , " setOnClickListener 结束动画" );
                // 关闭电门动画中
                slvv_touch.stopAnimStatus();
                // 显示
                showLL1();
            }
        });
        slvv_touch.initProgressbar( new CarStartAnimation3.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {

            }

            @Override
            public void onProgressSucced(final long time) {
                if (turnon == 0){
                    //zzt 关闭中 设置关闭动画
                    turnon = 1 ;
                    //zzt 关闭中 设置关闭动画
                    // 关闭失败设置启动状态
                    slvv_touch.setCloseStatus();
                    slvv_touch.setRun(false);
                    slvv_touch.stopAnimTime() ;
                }else if (turnon == 1){
                    //zzt 启动中 设置启动动画
                    turnon = 0 ;
                    //zzt 启动中 设置启动动画
                    // 启动失败设置关闭状态
                    slvv_touch. setOpenStatus(); ;
                    slvv_touch.setRun(false);
                    slvv_touch.stopAnimTime() ;
                }
            }

            @Override
            public void onProgressTimeout(){
                if (turnon == 0){
                    //zzt 关闭中 设置关闭动画
                    turnon = 1 ;
                }else if (turnon == 1){
                    //zzt 启动中 设置启动动画
                    turnon = 0 ;
                }
            }
        });
    }

}
