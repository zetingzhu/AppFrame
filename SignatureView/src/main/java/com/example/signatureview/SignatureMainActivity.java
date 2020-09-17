package com.example.signatureview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzt.commonmodule.utils.ConfigARouter;

import java.io.File;

@Route(path = ConfigARouter.ACTIVITY_HANDVIEW_MAIN)
public class SignatureMainActivity extends AppCompatActivity {
    SlideView view_slideview;
    private Button btnClear;
    private Button btnSave;
    private Button btn_add;

    SeekBar sb_slide;
    SlideViewUtil slideViewUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign);
        initView();
        FileUtils fileUtils = new FileUtils();
        fileUtils.getPath(SignatureMainActivity.this);

        slideViewUtil = new SlideViewUtil();
        ARouter.getInstance().inject(this);
    }

    private void initView() {
//        mSignatureView = findViewById(R.id.view_signature);
//        mMSignature = findViewById(R.id.gsv_signature);
        view_slideview = findViewById(R.id.view_slideview);
        sb_slide = findViewById(R.id.sb_slide);
        btnClear = findViewById(R.id.btn_clear);
        btnSave = findViewById(R.id.btn_save);
        btn_add = findViewById(R.id.btn_add);

        sb_slide.setMax(100);
        sb_slide.setProgress(0);
        sb_slide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                view_slideview.setWidthTranslate(BigDecimalUtil.div(progress, seekBar.getMax(), 2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mSignatureView.clear();
//                mMSignature.clear();
                view_slideview.clear();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view_slideview.getTouched()) {
                    File imgFile = slideViewUtil.getSaveImgPath(SignatureMainActivity.this, "zzt");
                    if (imgFile != null) {
//                        slideViewUtil.saveViewToFile(imgFile.getAbsolutePath(), view_slideview.clearBlank(view_slideview.getBitmap(), 50));
                        slideViewUtil.saveViewToFile(imgFile.getAbsolutePath(), view_slideview.getBitmap());
                        if (imgFile.exists()) {
                            Log.d(SlideViewUtil.TAG, "保存图片路据：" + imgFile.getAbsolutePath());
                        }
                    }
                } else {
                    Toast.makeText(SignatureMainActivity.this, "请先签名", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加大画布
                sb_slide.setProgress(0);
                view_slideview.addWidthScale();
            }
        });
    }

    private static final int REQUEST_CODE_PERMISSION = 101;


    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }
}
