package frame.zzt.com.appframe.rxjava;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import android.util.Log;

import java.io.File;

import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;
import frame.zzt.com.appframe.retrofit.ApiRetrofitWeather;
import frame.zzt.com.appframe.rxjava.download.FileDownLoadObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by allen on 18/8/31.
 */

public class RxUsePersenter extends BasePresenter<RxView> {
    private String TAG = "ActivityRxJavaUse";

    private String MIME_TYPE_APK = "application/vnd.android.package-archive";

    private Context mContext;

    public RxUsePersenter(RxView baseView, Context mCon) {
        super(baseView);
        this.mContext = mCon;
    }

    public RxUsePersenter(RxView baseView) {
        super(baseView);
    }

    /**
     * RX 下载文件
     *
     * @return
     */
    public Observable<File> downloadApkFile() {
        final FileDownLoadObserver<File> fileDownLoadObserver = new FileDownLoadObserver<File>() {
            @Override
            public void onDownLoadSuccess(File file) {
                Log.d(TAG, "onDownLoadSuccess：" + file.getName());
            }

            @Override
            public void onDownLoadFail(Throwable throwable) {
                Log.d(TAG, "onDownLoadFail：" + throwable);
            }

            @Override
            public void onProgress(int progress, long total) {
                Log.d(TAG, "onProgress - total:" + progress + " - total:" + total);
            }
        };

        final String destDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/fileDownload/";
        final String fileName = "file.apk";

        String url = "http://imtt.dd.qq.com/16891/533A07A5D7AA3015AD606FE4B901926A.apk?fsname=com.vipcare.niu_3.5.01_3050001.apk&csr=1bbd";
        ApiRetrofitWeather.getInstance().getApiService().download(url)
                .subscribeOn(Schedulers.io())//subscribeOn和ObserOn必须在io线程，如果在主线程会出错
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.computation())//需要
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(@NonNull ResponseBody responseBody) throws Exception {
                        Log.d(TAG, "apply - responseBody:" + responseBody.toString());
                        return fileDownLoadObserver.saveFile(responseBody, destDir, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileDownLoadObserver);

        return null;
    }

    // https://fir.im/zmp9?utm_source=fir&openId=oGB0Cj3aUUgAikrY24t0DywmPu6I
    // https://fir.im/zmp9?utm_source=fir&utm_medium=qr
    public void downloadInstallApk(String mApkUrl, String mNewVersion) {

    }

    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    public void installApk(File apkFile) {
        Intent installApkIntent = new Intent();
        installApkIntent.setAction(Intent.ACTION_VIEW);
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT);
        installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            /**
             * 7.0 调用系统不再允许使用Uri方式，应该替换为FileProvide
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            installApkIntent.setDataAndType(FileProvider.getUriForFile(mContext, getFileProviderAuthority(), apkFile), MIME_TYPE_APK);
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), MIME_TYPE_APK);
        }

        if (mContext.getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
            mContext.startActivity(installApkIntent);
        }
    }

    /**
     * 获取FileProvider的auth
     *
     * @return
     */
    public String getFileProviderAuthority() {
        try {
            for (ProviderInfo provider : mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_PROVIDERS).providers) {
                if (FileProvider.class.getName().equals(provider.name) && provider.authority.endsWith(".bga_update.file_provider")) {
                    return provider.authority;
                }
            }
        } catch (PackageManager.NameNotFoundException ignore) {
        }
        return null;
    }

    public boolean gotoNotificationAccessSetting(Context context) {
        try {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                intent.setComponent(cn);
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                context.startActivity(intent);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }


}