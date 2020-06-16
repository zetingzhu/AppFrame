package frame.zzt.com.appframe.mvvmbind;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableInt;

import java.util.List;

import frame.zzt.com.appframe.BR;
import frame.zzt.com.appframe.mvvmbind.adapter.ItemData;

public class MVVMData extends BaseObservable {

    // 显示文本
    private String Text1;
    private String Text2;
    // 点击数量
    private ObservableInt clickCount = new ObservableInt();
    // 列表信息
    private List<ItemData> mList;
    // 图片地址
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getText2() {
        return Text2;
    }

    public void setText2(String text2) {
        Text2 = text2;
    }

    public List<ItemData> getmList() {
        return mList;
    }

    public void setmList(List<ItemData> mList) {
        this.mList = mList;
    }

    @Bindable
    public String getText1() {
        return Text1;
    }

    public void setText1(String text1) {
        Text1 = text1;
        notifyPropertyChanged(BR.text1);
    }

    public ObservableInt getClickCount() {
        return clickCount;
    }

    public void setClickCount(ObservableInt clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "MVVMData{" +
                "Text1='" + Text1 + '\'' +
                ", Text2='" + Text2 + '\'' +
                ", clickCount=" + clickCount +
                ", mList=" + mList +
                '}';
    }
}
