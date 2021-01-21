package com.zzt.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;

import com.zzt.zztutillibrary.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/**
 * make                     : 制作吐司
 * make.setMode             : 设置模式
 * make.setGravity          : 设置位置
 * make.setBgColor          : 设置背景颜色
 * make.setBgResource       : 设置背景资源
 * make.setTextColor        : 设置字体颜色
 * make.setTextSize         : 设置字体大小
 * make.setDurationIsLong   : 设置是否长时间显示
 * make.setLeftIcon         : 设置左侧图标
 * make.setTopIcon          : 设置顶部图标
 * make.setRightIcon        : 设置右侧图标
 * make.setBottomIcon       : 设置底部图标
 * make.setNotUseSystemToast: 设置不使用系统吐司
 * make.show                : 显示吐司
 * getDefaultMaker          : 获取默认制作实例（控制 showShort、showLong 样式）
 * showShort                : 显示短时吐司
 * showLong                 : 显示长时吐司
 * cancel                   : 取消吐司显示
 */
public final class ToastUtils {

    @StringDef({MODE.LIGHT, MODE.DARK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MODE {
        String LIGHT = "light";
        String DARK = "dark";
    }

    private static final String TAG_TOAST = "TAG_TOAST";
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final String NULL = "toast null";
    private static final String NOTHING = "toast nothing";
    private static final ToastUtils DEFAULT_MAKER = make();

    private static IToast iToast;

    private String mMode;
    private int mGravity = -1;
    private int mXOffset = -1;
    private int mYOffset = -1;
    private int mBgColor = COLOR_DEFAULT;
    private int mBgResource = -1;
    private int mTextColor = COLOR_DEFAULT;
    private int mTextSize = -1;
    private Drawable[] mIcons = new Drawable[4];
    private boolean isNotUseSystemToast = false;

    /**
     * Make a toast.
     *
     * @return the single {@link ToastUtils} instance
     */
    public static ToastUtils make() {
        return new ToastUtils();
    }

    /**
     * @param mode The mode.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setMode(@MODE String mode) {
        mMode = mode;
        return this;
    }

    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setGravity(final int gravity, final int xOffset, final int yOffset) {
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
        return this;
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setBgColor(@ColorInt final int backgroundColor) {
        mBgColor = backgroundColor;
        return this;
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setBgResource(@DrawableRes final int bgResource) {
        mBgResource = bgResource;
        return this;
    }

    /**
     * Set the text color of toast.
     *
     * @param msgColor The text color of toast.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setTextColor(@ColorInt final int msgColor) {
        mTextColor = msgColor;
        return this;
    }

    /**
     * Set the text size of toast.
     *
     * @param textSize The text size of toast.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setTextSize(final int textSize) {
        mTextSize = textSize;
        return this;
    }


    /**
     * Set the left icon of toast.
     *
     * @param resId The left icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setLeftIcon(Context mContext, @DrawableRes int resId) {
        return setLeftIcon(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * Set the left icon of toast.
     *
     * @param drawable The left icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setLeftIcon(Drawable drawable) {
        mIcons[0] = drawable;
        return this;
    }

    /**
     * Set the top icon of toast.
     *
     * @param resId The top icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setTopIcon(Context mContext, @DrawableRes int resId) {
        return setTopIcon(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * Set the top icon of toast.
     *
     * @param drawable The top icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setTopIcon(Drawable drawable) {
        mIcons[1] = drawable;
        return this;
    }

    /**
     * Set the right icon of toast.
     *
     * @param resId The right icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setRightIcon(Context mContext, @DrawableRes int resId) {
        return setRightIcon(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * Set the right icon of toast.
     *
     * @param drawable The right icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setRightIcon(Drawable drawable) {
        mIcons[2] = drawable;
        return this;
    }

    /**
     * Set the left bottom of toast.
     *
     * @param resId The bottom icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setBottomIcon(Context mContext, int resId) {
        return setBottomIcon(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * Set the bottom icon of toast.
     *
     * @param drawable The bottom icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setBottomIcon(Drawable drawable) {
        mIcons[3] = drawable;
        return this;
    }

    /**
     * Set not use system toast.
     *
     * @return the single {@link ToastUtils} instance
     */
    public final ToastUtils setNotUseSystemToast() {
        isNotUseSystemToast = true;
        return this;
    }

    /**
     * Return the default {@link ToastUtils} instance.
     *
     * @return the default {@link ToastUtils} instance
     */
    public static ToastUtils getDefaultMaker() {
        return DEFAULT_MAKER;
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public final void show(AppCompatActivity mActivity, final CharSequence text, int duration) {
        show(mActivity, text, duration, this);
    }

    /**
     * Show custom toast.
     */
    public final void show(AppCompatActivity mActivity, final View view, int duration) {
        show(mActivity, view, duration, this);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(AppCompatActivity mActivity, final CharSequence text) {
        show(mActivity, text, Toast.LENGTH_SHORT, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(AppCompatActivity mActivity, final CharSequence text) {
        show(mActivity, text, Toast.LENGTH_LONG, DEFAULT_MAKER);
    }

    private View tryApplyUtilsToastView(Context mContext, final CharSequence text) {
        if (!MODE.DARK.equals(mMode) && !MODE.LIGHT.equals(mMode)
                && mIcons[0] == null && mIcons[1] == null && mIcons[2] == null && mIcons[3] == null) {
            return null;
        }

        View toastView = ViewUtils.layoutId2View(mContext, R.layout.utils_toast_view);
        TextView messageTv = toastView.findViewById(android.R.id.message);
        if (MODE.DARK.equals(mMode)) {
            GradientDrawable bg = (GradientDrawable) toastView.getBackground().mutate();
            bg.setColor(Color.parseColor("#BB000000"));
            messageTv.setTextColor(Color.WHITE);
        }
        messageTv.setText(text);
        if (mIcons[0] != null) {
            View leftIconView = toastView.findViewById(R.id.utvLeftIconView);
            ViewCompat.setBackground(leftIconView, mIcons[0]);
            leftIconView.setVisibility(View.VISIBLE);
        }
        if (mIcons[1] != null) {
            View topIconView = toastView.findViewById(R.id.utvTopIconView);
            ViewCompat.setBackground(topIconView, mIcons[1]);
            topIconView.setVisibility(View.VISIBLE);
        }
        if (mIcons[2] != null) {
            View rightIconView = toastView.findViewById(R.id.utvRightIconView);
            ViewCompat.setBackground(rightIconView, mIcons[2]);
            rightIconView.setVisibility(View.VISIBLE);
        }
        if (mIcons[3] != null) {
            View bottomIconView = toastView.findViewById(R.id.utvBottomIconView);
            ViewCompat.setBackground(bottomIconView, mIcons[3]);
            bottomIconView.setVisibility(View.VISIBLE);
        }
        return toastView;
    }

    /**
     * Cancel the toast.
     */
    public static void cancel() {
        if (iToast != null) {
            iToast.cancel();
            iToast = null;
        }
    }

    private static void show(AppCompatActivity mActivity, final CharSequence text, final int duration, final ToastUtils utils) {
        show(mActivity, null, getToastFriendlyText(text), duration, utils);
    }

    private static void show(AppCompatActivity mActivity, final View view, final int duration, final ToastUtils utils) {
        show(mActivity, view, null, duration, utils);
    }

    private static void show(final AppCompatActivity mActivity, @Nullable final View view, final CharSequence text, final int duration, final ToastUtils utils) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cancel();
                iToast = newToast(mActivity, utils);
                if (view != null) {
                    iToast.setToastView(view);
                } else {
                    iToast.setToastView(text);
                }
                iToast.show(duration);
            }
        });
    }

    private static CharSequence getToastFriendlyText(CharSequence src) {
        CharSequence text = src;
        if (text == null) {
            text = NULL;
        } else if (text.length() == 0) {
            text = NOTHING;
        }
        return text;
    }

    private static IToast newToast(AppCompatActivity mActivity, ToastUtils toastUtils) {
        if (!toastUtils.isNotUseSystemToast) {
            if (NotificationManagerCompat.from(mActivity).areNotificationsEnabled()) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return new SystemToast(mActivity, toastUtils);
                }
                if (!PermissionUtils.isGrantedDrawOverlays(mActivity)) {
                    return new SystemToast(mActivity, toastUtils);
                }
            }
        }

        // not use system or notification disable
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return new WindowManagerToast(mActivity, toastUtils, WindowManager.LayoutParams.TYPE_TOAST);
        } else if (PermissionUtils.isGrantedDrawOverlays(mActivity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                new WindowManagerToast(mActivity, toastUtils, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                new WindowManagerToast(mActivity, toastUtils, WindowManager.LayoutParams.TYPE_PHONE);
            }
        }
        return new ActivityToast(mActivity, toastUtils);
    }

    static final class SystemToast extends AbsToast {

        SystemToast(AppCompatActivity mActivity, ToastUtils toastUtils) {
            super(mActivity, toastUtils);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                try {
                    //noinspection JavaReflectionMemberAccess
                    Field mTNField = Toast.class.getDeclaredField("mTN");
                    mTNField.setAccessible(true);
                    Object mTN = mTNField.get(mToast);
                    Field mTNmHandlerField = mTNField.getType().getDeclaredField("mHandler");
                    mTNmHandlerField.setAccessible(true);
                    Handler tnHandler = (Handler) mTNmHandlerField.get(mTN);
                    mTNmHandlerField.set(mTN, new SafeHandler(tnHandler));
                } catch (Exception ignored) {/**/}
            }
        }

        @Override
        public void show(int duration) {
            if (mToast == null) return;
            mToast.setDuration(duration);
            mToast.show();
        }

        static class SafeHandler extends Handler {
            private Handler impl;

            SafeHandler(Handler impl) {
                this.impl = impl;
            }

            @Override
            public void handleMessage(@NonNull Message msg) {
                impl.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(@NonNull Message msg) {
                try {
                    impl.dispatchMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static final class WindowManagerToast extends AbsToast {

        private WindowManager mWM;

        private WindowManager.LayoutParams mParams;

        WindowManagerToast(AppCompatActivity mActivity, ToastUtils toastUtils, int type) {
            super(mActivity, toastUtils);
            mParams = new WindowManager.LayoutParams();
            mParams.type = type;
        }

        @Override
        public void show(final int duration) {
            if (mToast == null) return;
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.format = PixelFormat.TRANSLUCENT;
            mParams.windowAnimations = android.R.style.Animation_Toast;
            mParams.setTitle("ToastWithoutNotification");
            mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mParams.packageName = mActivity.getPackageName();

            mParams.gravity = mToast.getGravity();
            if ((mParams.gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f;
            }
            if ((mParams.gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f;
            }

            mParams.x = mToast.getXOffset();
            mParams.y = mToast.getYOffset();
            mParams.horizontalMargin = mToast.getHorizontalMargin();
            mParams.verticalMargin = mToast.getVerticalMargin();

            mWM = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
            try {
                if (mWM != null) {
                    mWM.addView(mToastView, mParams);
                }
            } catch (Exception ignored) {/**/}

            ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            }, duration == Toast.LENGTH_SHORT ? 2000 : 3500);
        }

        @Override
        public void cancel() {
            try {
                if (mWM != null) {
                    mWM.removeViewImmediate(mToastView);
                    mWM = null;
                }
            } catch (Exception ignored) {/**/}
            super.cancel();
        }
    }

    static final class ActivityToast extends AbsToast {

        private static int sShowingIndex = 9876;

        ActivityToast(AppCompatActivity mActivity, ToastUtils toastUtils) {
            super(mActivity, toastUtils);
        }

        @Override
        public void show(int duration) {
            if (mToast == null) return;
            if (mLifecycleRegistry.getCurrentState() == Lifecycle.State.STARTED) {
                showSystemToast(mActivity, duration);
                return;
            }
            if (ActivityUtils.isActivityAlive(mActivity)) {
                showWithActivity(mActivity, sShowingIndex, false);
            } else {
                // try to use system toast
                showSystemToast(mActivity, duration);
            }
        }

        @Override
        public void cancel() {
            if (ActivityUtils.isActivityAlive(mActivity)) {
                final Window window = mActivity.getWindow();
                if (window != null) {
                    ViewGroup decorView = (ViewGroup) window.getDecorView();
                    View toastView = decorView.findViewWithTag(TAG_TOAST + sShowingIndex);
                    if (toastView != null) {
                        try {
                            decorView.removeView(toastView);
                        } catch (Exception ignored) {/**/}
                    }
                }
            }
            super.cancel();
        }

        private void showSystemToast(AppCompatActivity mActivity, int duration) {
            SystemToast systemToast = new SystemToast(mActivity, mToastUtils);
            systemToast.mToast = mToast;
            systemToast.show(duration);
        }

        private void showWithActivity(final Activity activity, final int index, boolean useAnim) {
            final Window window = activity.getWindow();
            if (window != null) {
                final ViewGroup decorView = (ViewGroup) window.getDecorView();
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.gravity = mToast.getGravity();
                lp.bottomMargin = mToast.getYOffset() + BarUtils.getNavBarHeight(activity);
                lp.leftMargin = mToast.getXOffset();
                View toastViewSnapshot = getToastViewSnapshot(index);
                if (useAnim) {
                    toastViewSnapshot.setAlpha(0);
                    toastViewSnapshot.animate().alpha(1).setDuration(200).start();
                }
                decorView.addView(toastViewSnapshot, lp);
            }
        }

        private View getToastViewSnapshot(final int index) {
            Bitmap bitmap = ImageUtils.view2Bitmap(mToastView);
            ImageView toastIv = new ImageView(mActivity);
            toastIv.setTag(TAG_TOAST + index);
            toastIv.setImageBitmap(bitmap);
            return toastIv;
        }
    }

    static abstract class AbsToast implements IToast {

        protected Toast mToast;
        protected ToastUtils mToastUtils;
        protected View mToastView;
        protected AppCompatActivity mActivity;
        protected Lifecycle mLifecycleRegistry;

        AbsToast(AppCompatActivity act, ToastUtils toastUtils) {
            mActivity = act;
            this.mLifecycleRegistry = act.getLifecycle();
            mToast = new Toast(mActivity);
            mToastUtils = toastUtils;

            if (mToastUtils.mGravity != -1 || mToastUtils.mXOffset != -1 || mToastUtils.mYOffset != -1) {
                mToast.setGravity(mToastUtils.mGravity, mToastUtils.mXOffset, mToastUtils.mYOffset);
            }
        }

        @Override
        public void setToastView(View view) {
            mToastView = view;
            mToast.setView(mToastView);
        }

        @Override
        public void setToastView(CharSequence text) {
            View utilsToastView = mToastUtils.tryApplyUtilsToastView(mActivity, text);
            if (utilsToastView != null) {
                setToastView(utilsToastView);
                return;
            }

            mToastView = mToast.getView();
            if (mToastView == null || mToastView.findViewById(android.R.id.message) == null) {
                setToastView(ViewUtils.layoutId2View(mActivity, R.layout.utils_toast_view));
            }

            TextView messageTv = mToastView.findViewById(android.R.id.message);
            messageTv.setText(text);
            if (mToastUtils.mTextColor != COLOR_DEFAULT) {
                messageTv.setTextColor(mToastUtils.mTextColor);
            }
            if (mToastUtils.mTextSize != -1) {
                messageTv.setTextSize(mToastUtils.mTextSize);
            }
            setBg(messageTv);
        }

        protected void setBg(final TextView msgTv) {
            if (mToastUtils.mBgResource != -1) {
                mToastView.setBackgroundResource(mToastUtils.mBgResource);
                msgTv.setBackgroundColor(Color.TRANSPARENT);
            } else if (mToastUtils.mBgColor != COLOR_DEFAULT) {
                Drawable toastBg = mToastView.getBackground();
                Drawable msgBg = msgTv.getBackground();
                if (toastBg != null && msgBg != null) {
                    toastBg.mutate().setColorFilter(new PorterDuffColorFilter(mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                    msgTv.setBackgroundColor(Color.TRANSPARENT);
                } else if (toastBg != null) {
                    toastBg.mutate().setColorFilter(new PorterDuffColorFilter(mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                } else if (msgBg != null) {
                    msgBg.mutate().setColorFilter(new PorterDuffColorFilter(mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                } else {
                    mToastView.setBackgroundColor(mToastUtils.mBgColor);
                }
            }
        }

        @Override
        @CallSuper
        public void cancel() {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = null;
            mToastView = null;
        }
    }

    interface IToast {

        void setToastView(View view);

        void setToastView(CharSequence text);

        void show(int duration);

        void cancel();
    }

}