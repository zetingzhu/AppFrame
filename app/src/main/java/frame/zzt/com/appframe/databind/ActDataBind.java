package frame.zzt.com.appframe.databind;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.databinding.ActivityDatabindingBinding;

public class ActDataBind extends AppCompatActivity {
    private final static String TAG = ActDataBind.class.getSimpleName();

    ActivityDatabindingBinding databindingBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定方式
        databindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);

        databindingBinding.setUser(getUsData());
        initView();
    }

    private void initView() {
        databindingBinding.etDatabinging1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG, "改变后状态：" + databindingBinding.getUser().getShowText());
            }
        });

        databindingBinding.btnNotify.setOnClickListener(v -> databindingBinding.setUser(getUsData2()));
        databindingBinding.btnNotify2.setOnClickListener(v -> {
            getUsData3(databindingBinding.getUser());
        });
    }

    public void btnNotify1(View v) {

    }


    /**
     * 获取初始化数据
     *
     * @return
     */
    public UsData getUsData() {
        UsData usData = new UsData();
        usData.setName("张三");
        usData.setAge(11);

        List<String> mList = new ArrayList<>();
        mList.add("唱歌");
        mList.add("读书");
        mList.add("跳舞");
        usData.setHobby(mList);

        return usData;
    }

    public UsData getUsData2() {
        UsData usData = new UsData();
        usData.setName("张小三");
        usData.setAge(110);

        List<String> mList = new ArrayList<>();
        mList.add("唱歌 0");
        mList.add("读书 1");
        mList.add("跳舞 2");
        usData.setHobby(mList);

        return usData;
    }

    public void getUsData3(UsData usData) {
        usData.setName("张三四");
        usData.setAge(12345);
    }

}
