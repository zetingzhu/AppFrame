package frame.zzt.com.appframe.seekbar;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import frame.zzt.com.appframe.R;


/**
 * 自定义 滑动图片在上方的滑动条
 * Created by zeting
 * Date 19/4/15.
 */

public class MySeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private static final String TAG = MySeekBar.class.getSimpleName();

    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private float mTitleTextSize;
    private String mTitleText;//文字的内容

    /**
     * 背景图片
     */
    private int img;
    private Bitmap map;
    private Bitmap start, end;
    //bitmap对应的宽高
    private float img_width, img_height, imgStartWidth;
    Paint paint;

    private float numTextWidth;
    //测量seekbar的规格
    private Rect rect_seek;
    private Paint.FontMetrics fm;

    public static final int TEXT_ALIGN_LEFT = 0x00000001;
    public static final int TEXT_ALIGN_RIGHT = 0x00000010;
    public static final int TEXT_ALIGN_CENTER_VERTICAL = 0x00000100;
    public static final int TEXT_ALIGN_CENTER_HORIZONTAL = 0x00001000;
    public static final int TEXT_ALIGN_TOP = 0x00010000;
    public static final int TEXT_ALIGN_BOTTOM = 0x00100000;
    /**
     * 文本中轴线X坐标
     */
    private float textCenterX;
    /**
     * 文本baseline线Y坐标
     */
    private float textBaselineY;
    /**
     * 文字的方位
     */
    private int textAlign;

    /**
     * 位移
     */
    private float mProgressFloat;

    public MySeekBar(Context context) {
        this(context, null);
        initView(context);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    public void initView(Context mContext) {

//        img_width =  DensityUtil.px2dip( mContext , 15f ) ;
//        img_height = DensityUtil.px2dip( mContext , 23f ) ;

        mTitleTextSize = 30;
        mTitleTextColor = Color.RED;
        img = R.drawable.location_vehicle_annotation;
        getImgWH();
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setTextSize(mTitleTextSize);//设置文字大小
        paint.setColor(mTitleTextColor);//设置文字颜色
        //设置控件的padding 给提示文字留出位置
        setPadding((int) Math.ceil(img_width) / 2, (int) Math.ceil(img_height) - 2, (int) Math.ceil(img_height) / 2, (int) (Math.ceil(imgStartWidth) / 2 + 3));
        textAlign = TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL;
    }

    /**
     * 获取图片的宽高
     */
    private void getImgWH() {
        map = BitmapFactory.decodeResource(getResources(), img);
        start = BitmapFactory.decodeResource(getResources(), R.drawable.mytrip_start_point);
        end = BitmapFactory.decodeResource(getResources(), R.drawable.mytrip_end_point);
//        img_width = map.getWidth();
//        img_height = map.getHeight();
        img_width = 45;
        img_height = 69;
        imgStartWidth = 24;

        Log.d(TAG, "img_width:" + img_width + " - img_height:" + img_height);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTextLocation();//定位文本绘制的位置

        rect_seek = this.getProgressDrawable().getBounds();

        Log.d(TAG, "getProgressFloat():" + getProgressFloat());

        //定位文字背景图片的位置
        float bm_x = rect_seek.width() * getProgressFloat() / getMax();
        float bm_y = rect_seek.height();
//    //计算文字的中心位置在bitmap
        float text_x = rect_seek.width() * getProgressFloat() / getMax() + (img_width - numTextWidth) / 2;

        Rect rectStart = new Rect((int) (img_width / 2 - imgStartWidth / 2), (int) (img_height - imgStartWidth / 2), (int) (img_width / 2 + imgStartWidth / 2), (int) (img_height + imgStartWidth / 2));
        Rect rectEnd = new Rect((int) (rect_seek.width() + imgStartWidth / 2), (int) (img_height - imgStartWidth / 2), (int) (rect_seek.width() + imgStartWidth / 2 + imgStartWidth), (int) (img_height + imgStartWidth / 2));
        canvas.drawBitmap(start, null, rectStart, paint);
        canvas.drawBitmap(end, null, rectEnd, paint);

        Rect rectSrc = new Rect((int) (bm_x), 0, (int) (bm_x + img_width), (int) img_height);
//        canvas.drawBitmap(map, bm_x, bm_y, paint);//画背景图
        canvas.drawBitmap(map, null, rectSrc, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mTitleText, bm_x + textCenterX, textBaselineY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();//监听手势滑动，不断重绘文字和背景图的显示位置
        return super.onTouchEvent(event);
    }

    public synchronized void setProgressFloat(Float progress) {
        this.mProgressFloat = progress;
        super.setProgress((int) mProgressFloat);
        postInvalidate();
    }

    /**
     * 获取进度
     *
     * @return
     */
    public float getProgressFloat() {
        return mProgressFloat;
    }

    @Override
    public synchronized void setProgress(int progress) {
        this.mProgressFloat = progress;
        super.setProgress(progress);
    }


    /**
     * 定位文本绘制的位置
     */
    private void setTextLocation() {

        fm = paint.getFontMetrics();
        //文本的宽度
        mTitleText = getProgress() + "";

        numTextWidth = paint.measureText(mTitleText);

        float textCenterVerticalBaselineY = img_height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        switch (textAlign) {
            case TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL:
                textCenterX = img_width / 2;
                textBaselineY = textCenterVerticalBaselineY;
                break;
            case TEXT_ALIGN_LEFT | TEXT_ALIGN_CENTER_VERTICAL:
                textCenterX = numTextWidth / 2;
                textBaselineY = textCenterVerticalBaselineY;
                break;
            case TEXT_ALIGN_RIGHT | TEXT_ALIGN_CENTER_VERTICAL:
                textCenterX = img_width - numTextWidth / 2;
                textBaselineY = textCenterVerticalBaselineY;
                break;
            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_CENTER_HORIZONTAL:
                textCenterX = img_width / 2;
                textBaselineY = img_height - fm.bottom;
                break;
            case TEXT_ALIGN_TOP | TEXT_ALIGN_CENTER_HORIZONTAL:
                textCenterX = img_width / 2;
                textBaselineY = -fm.ascent;
                break;
            case TEXT_ALIGN_TOP | TEXT_ALIGN_LEFT:
                textCenterX = numTextWidth / 2;
                textBaselineY = -fm.ascent;
                break;
            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_LEFT:
                textCenterX = numTextWidth / 2;
                textBaselineY = img_height - fm.bottom;
                break;
            case TEXT_ALIGN_TOP | TEXT_ALIGN_RIGHT:
                textCenterX = img_width - numTextWidth / 2;
                textBaselineY = -fm.ascent;
                break;
            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_RIGHT:
                textCenterX = img_width - numTextWidth / 2;
                textBaselineY = img_height - fm.bottom;
                break;
        }
    }
}