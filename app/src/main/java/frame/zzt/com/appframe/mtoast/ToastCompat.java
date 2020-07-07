package frame.zzt.com.appframe.mtoast;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import frame.zzt.com.appframe.MyApplication;

/**
 * @author: zeting
 * @date: 2020/7/2
 * toast 工具封装工具
 */
public class ToastCompat implements IToast {

    private static final String TAG = ToastCompat.class.getSimpleName();
    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private IToast mIToast;

    public ToastCompat(Context context) {
        this(context, null, -1);
    }

    ToastCompat(Context context, String text, int duration) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            checkNotification = SysUtils.isNotificationEnabled(MyApplication.getInstance()) ? 0 : 1;
//        }
//        if (checkNotification == 0) {
//            mIToast = SystemToast.makeText(context, text, duration);
//        } else {
        mIToast = CustomToast.makeText(context, text, duration);
//        }
    }

    public static IToast makeText(Context context, String text, int duration) {
        return new ToastCompat(context, text, duration);
    }

    public static IToast makeText(Context context, int resId, int duration) {
        return new ToastCompat(context, context.getString(resId), duration);
    }

    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        return mIToast.setGravity(gravity, xOffset, yOffset);
    }

    @Override
    public IToast setDuration(long durationMillis) {
        return mIToast.setDuration(durationMillis);
    }

    @Override
    public IToast setView(View view) {
        return mIToast.setView(view);
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        return mIToast.setMargin(horizontalMargin, verticalMargin);
    }

    @Override
    public IToast setText(String text) {
        return mIToast.setText(text);
    }

    @Override
    public void show() {
        mIToast.show();
    }

    @Override
    public void cancel() {
        mIToast.cancel();
    }

}
