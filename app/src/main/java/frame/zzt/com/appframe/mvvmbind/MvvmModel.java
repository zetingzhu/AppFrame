package frame.zzt.com.appframe.mvvmbind;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import frame.zzt.com.appframe.mvvmbind.adapter.ItemData;

public class MvvmModel extends ViewModel {

    MutableLiveData<MVVMData> data = new MutableLiveData<>();

    public void getData(MVVMData mvvmData) {

        Random random = new Random();
        mvvmData.setText1("测试数据" + random.nextInt(100));
        mvvmData.setText2("T2：" + random.nextInt(100));
        data.postValue(mvvmData);
    }

    /**
     * 修改列表的值
     */
    public void updataListData(MVVMData mvvmData) {
        Random random = new Random();
        List<ItemData> itemDataList = new ArrayList<>();
        itemDataList.add(new ItemData(ItemData.TYPE_1, "修改标题" + random.nextInt(100), "描述详细的内容"));
        itemDataList.add(new ItemData(ItemData.TYPE_2, "修改标题" + random.nextInt(100), "描述详细的内容"));
        itemDataList.add(new ItemData(ItemData.TYPE_3, "修改标题" + random.nextInt(100), "描述详细的内容"));
        mvvmData.setmList(itemDataList);
        data.postValue(mvvmData);
    }

    /**
     * 添加列表数据
     *
     * @param mvvmData
     */
    public void addListData(MVVMData mvvmData) {
        if (mvvmData.getmList() != null) {
            Random random = new Random();
            mvvmData.getmList().add(new ItemData(random.nextInt(3), "添加标题" + random.nextInt(100), "描述详细的内容"));
            data.postValue(mvvmData);
        }
    }

    /**
     * 第三个按钮点击事件
     */
    public void btnTextClick3(MVVMData mvvmData) {
        getData(mvvmData);
        mvvmData.getClickCount().set(mvvmData.getClickCount().get() + 1);
    }


}
