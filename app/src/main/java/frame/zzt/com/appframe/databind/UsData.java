package frame.zzt.com.appframe.databind;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.BR;

public class UsData extends BaseObservable {
    // 姓名
    private String name = "";
    // 年龄
    private int age;
    // 爱好
    private List<String> hobby = new ArrayList<>();

    private String showText;

    @Bindable
    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
        notifyPropertyChanged(BR.showText);
    }

    public void noticeShowText() {
        setShowText("姓名：" + name + " - 年龄：" + String.valueOf(age) + "\n爱好：" + hobby.toString());
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        noticeShowText();
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        noticeShowText();
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
        noticeShowText();
        notifyPropertyChanged(BR.hobby);
    }
}
