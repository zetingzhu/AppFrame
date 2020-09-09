package frame.zzt.com.appframe.doodleview;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import frame.zzt.com.appframe.R;

/**
 * 用于展示 自定义涂鸦 功能的 Activity
 */

public class DoodleViewActivity extends AppCompatActivity {

    private DoodleView mDoodleView;
    private AlertDialog mColorDialog;
    private AlertDialog mPaintDialog;
    private AlertDialog mShapeDialog;

    private static final String TAG = "DoodleViewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodleview);

        mDoodleView = (DoodleView) findViewById(R.id.doodle_doodleview);
        mDoodleView.setSize(dip2px(5));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDoodleView.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_color:
                showColorDialog();
                break;
            case R.id.main_size:
                showSizeDialog();
                break;
            case R.id.main_action:
                showShapeDialog();
                break;
            case R.id.main_reset:
                mDoodleView.reset();
                break;
            case R.id.main_save:
                // 申请权限的使用
                if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 没有权限，申请权限。
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                        Log.i(TAG, "用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。");
                    } else {
                        Log.i(TAG, "申请权限");
                        //  申请读取系统磁盘权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission_group.STORAGE}, 1101);
                    }
                } else {
                    // 有权限了，去放肆吧。

                    String path = mDoodleView.saveBitmap(mDoodleView);
                    Log.d(TAG, "onOptionsItemSelected: " + path);
                    Toast.makeText(this, "保存图片的路径为：" + path, Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以去放肆了。
                    Log.i(TAG, "申请权限 成功");
                } else {
                    // 权限被用户拒绝了，洗洗睡吧。
                    Log.i(TAG, "申请权限 失败");
                }
                return;
            }
        }
    }

    /**
     * 显示选择画笔颜色的对话框
     */
    private void showColorDialog() {
        if (mColorDialog == null) {
            mColorDialog = new AlertDialog.Builder(this)
                    .setTitle("选择颜色")
                    .setSingleChoiceItems(new String[]{"蓝色", "红色", "黑色"}, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            mDoodleView.setColor("#0000ff");
                                            break;
                                        case 1:
                                            mDoodleView.setColor("#ff0000");
                                            break;
                                        case 2:
                                            mDoodleView.setColor("#272822");
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mColorDialog.show();
    }

    /**
     * 显示选择画笔粗细的对话框
     */
    private void showSizeDialog() {
        if (mPaintDialog == null) {
            mPaintDialog = new AlertDialog.Builder(this)
                    .setTitle("选择画笔粗细")
                    .setSingleChoiceItems(new String[]{"细", "中", "粗"}, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            mDoodleView.setSize(dip2px(5));
                                            break;
                                        case 1:
                                            mDoodleView.setSize(dip2px(10));
                                            break;
                                        case 2:
                                            mDoodleView.setSize(dip2px(15));
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mPaintDialog.show();
    }

    /**
     * 显示选择画笔形状的对话框
     */
    private void showShapeDialog() {
        if (mShapeDialog == null) {
            mShapeDialog = new AlertDialog.Builder(this)
                    .setTitle("选择形状")
                    .setSingleChoiceItems(new String[]{"路径", "直线", "矩形", "圆形", "实心矩形", "实心圆"}, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            mDoodleView.setType(DoodleView.ActionType.Path);
                                            break;
                                        case 1:
                                            mDoodleView.setType(DoodleView.ActionType.Line);
                                            break;
                                        case 2:
                                            mDoodleView.setType(DoodleView.ActionType.Rect);
                                            break;
                                        case 3:
                                            mDoodleView.setType(DoodleView.ActionType.Circle);
                                            break;
                                        case 4:
                                            mDoodleView.setType(DoodleView.ActionType.FillEcRect);
                                            break;
                                        case 5:
                                            mDoodleView.setType(DoodleView.ActionType.FilledCircle);
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mShapeDialog.show();
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
