package frame.zzt.com.appframe.databind;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.databinding.ActivityDatabindingDoubleBinding;

public class ActDoubleDataBind extends AppCompatActivity {
    private final static String TAG = ActDoubleDataBind.class.getSimpleName();

    ActivityDatabindingDoubleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定方式
        binding = ActivityDatabindingDoubleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setUser(getUserData());

        initView();
    }

    private void initView() {
        binding.etDatabinging1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG, "改变后状态：" + binding.getUser().getShowText().get());
            }
        });

        binding.btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserData3(binding.getUser());
            }
        });
        binding.btnNotify2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.setUser(getUserData2());
            }
        });

    }


    public DoubleUserData getUserData() {
        DoubleUserData usData = new DoubleUserData();
        usData.getName().set("李四");
        usData.getAge().set(12);

        List<String> mList = new ArrayList<>();
        mList.add("跑步");
        mList.add("舞剑");
        mList.add("街舞");
        usData.getHobby().addAll(mList);

        return usData;
    }

    public DoubleUserData getUserData2() {
        DoubleUserData usData = new DoubleUserData();
        usData.getName().set("李大四");
        usData.getAge().set(120);

        List<String> mList = new ArrayList<>();
        mList.add("跑步 1");
        mList.add("舞剑 1");
        mList.add("街舞 1");
        usData.getHobby().addAll(mList);

        return usData;
    }

    public void getUserData3(DoubleUserData doubleUserData) {
        doubleUserData.getName().set("李四五");
        doubleUserData.getAge().set(99877);
    }
}
