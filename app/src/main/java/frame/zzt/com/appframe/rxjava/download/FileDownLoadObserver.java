package frame.zzt.com.appframe.rxjava.download;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

/**
 * Created by allen on 18/9/10.
 */

public abstract class FileDownLoadObserver<T> extends DefaultObserver<T> {

    private String TAG = "FileDownLoadObserver";

    @Override
    public void onNext(T t) {
        onDownLoadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDownLoadFail(e);
    }

    //可以重写，具体可由子类实现
    @Override
    public void onComplete() {
    }

    //下载成功的回调
    public abstract void onDownLoadSuccess(T t);

    //下载失败回调
    public abstract void onDownLoadFail(Throwable throwable);

    //下载进度监听
    public abstract void onProgress(int progress, long total);

    /**
     * 将文件写入本地
     *
     * @param responseBody 请求结果全体
     * @param destFileDir  目标文件夹
     * @param destFileName 目标文件名
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public File saveFile(ResponseBody responseBody, String destFileDir, String destFileName) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = responseBody.byteStream();
            final long total = responseBody.contentLength();
            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                boolean boo = dir.mkdirs();
                Log.i(TAG, "创建文件是否成功" + boo);
            }
            File file = new File(dir, destFileName);

            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                //这里就是对进度的监听回调
                onProgress((int) (finalSum * 100 / total), total);
            }
            fos.flush();

            return file;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
