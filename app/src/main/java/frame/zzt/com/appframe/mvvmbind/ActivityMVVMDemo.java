package frame.zzt.com.appframe.mvvmbind;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.databinding.ActivityMvvmDemoBinding;
import frame.zzt.com.appframe.mvvmbind.adapter.ItemData;
import frame.zzt.com.appframe.mvvmbind.adapter.MvRecyclerAdapter;

public class ActivityMVVMDemo extends AppCompatActivity {
    private static final String TAG = ActivityMVVMDemo.class.getSimpleName();

    String url = "http://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png";
    String url1 = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2723295363,2407366600&fm=26&gp=0.jpg";

    ActivityMvvmDemoBinding binding;

    MVVMData mvvmData;
    MvvmModel mvvmModel;

    // 列表适配
    MvRecyclerAdapter adapter;

    public class MyHandlers {

        /**
         * 第二个按钮点击事件
         */
        public void onClickFriend(View view) {
            Log.i(TAG, "onClickFriend 被点击了");
            int count = mvvmData.getClickCount().get() + 1;
            mvvmData.getClickCount().set(count);
            mvvmData.setText2("T2" + +count);


            AlertDialog alertDialog;
            new AlertDialog.Builder(ActivityMVVMDemo.this).setTitle("")
                    .setTitle("1111")
                    .create();


        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMvvmDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();
    }

    private void initData() {
        mvvmData = new MVVMData();
        mvvmData.setText1("默认值");
        mvvmData.setPicUrl(url);
        binding.setVmData(mvvmData);

        mvvmData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i(TAG, "mvvmData 属性改变：" + sender.toString() + " - :" + propertyId);
            }
        });
        binding.getVmData().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i(TAG, "binding.getVmData() 属性改变：" + sender.toString() + " - :" + propertyId);
            }
        });

//        mvvmModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MvvmModel.class);
        mvvmModel = new ViewModelProvider(this).get(MvvmModel.class);
        mvvmModel.data.observe(this, new Observer<MVVMData>() {
            @Override
            public void onChanged(MVVMData mData) {
                mvvmData = mData;
                binding.setVmData(mvvmData);
                if (mvvmData.getmList() != null && !mvvmData.getmList().isEmpty()) {
                    adapter.notifyAdapter(mvvmData.getmList());
                }
            }
        });

        binding.setHandlers(new MyHandlers());
        binding.setVmModel(mvvmModel);

        /**
         * 第四个按钮点击事件
         */
        binding.btnClick4.setOnClickListener(v -> {
            mvvmModel.getData(mvvmData);
            mvvmData.getClickCount().set(mvvmData.getClickCount().get() + 1);
        });

        // 加载recycle列表
        List<ItemData> itemDataList = new ArrayList<>();
        itemDataList.add(new ItemData(ItemData.TYPE_1, "我是标题", "描述详细的内容"));
        itemDataList.add(new ItemData(ItemData.TYPE_2, "我是标题", "描述详细的内容"));
        itemDataList.add(new ItemData(ItemData.TYPE_3, "我是标题", "描述详细的内容"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.vmRecyclerview.setLayoutManager(layoutManager);
        binding.vmRecyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter = new MvRecyclerAdapter(itemDataList);
        binding.vmRecyclerview.setAdapter(adapter);
    }

    private void initView() {

    }

    /**
     * 第一个按钮点击事件
     */
    public void btnClick1(View view) {
        mvvmModel.getData(mvvmData);
        mvvmData.getClickCount().set(mvvmData.getClickCount().get() + 1);
    }
}
