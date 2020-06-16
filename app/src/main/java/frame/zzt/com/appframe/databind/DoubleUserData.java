package frame.zzt.com.appframe.databind;

import android.util.Log;

import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

public class DoubleUserData {
    private static final String TAG = DoubleUserData.class.getSimpleName();

    // 姓名
    private ObservableField<String> name = new ObservableField<>();
    // 年龄
    private ObservableInt age = new ObservableInt();
    // 爱好
    private ObservableArrayList<String> hobby = new ObservableArrayList<>();

    private ObservableField<String> showText = new ObservableField<>();

    public DoubleUserData() {
        name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i(TAG, "name 属性改变：" + ((ObservableField<String>) sender).get() + " - :" + propertyId);
                noticeShowText();
            }
        });

        age.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i(TAG, "age 属性改变：" + ((ObservableInt) sender).get() + " - :" + propertyId);
                noticeShowText();
            }
        });

        hobby.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<String>>() {
            @Override
            public void onChanged(ObservableList<String> sender) {
                Log.i(TAG, "hobby 属性改变：" + ((ObservableInt) sender).get());
                noticeShowText();
            }

            @Override
            public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {

            }
        });
    }

    public ObservableField<String> getShowText() {
        return showText;
    }

    public void setShowText(ObservableField<String> showText) {
        this.showText = showText;
    }

    public void noticeShowText() {
        getShowText().set("姓名：" + name.get() + " - 年龄：" + String.valueOf(age.get()) + "\n爱好：" + hobby.toString());
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableInt getAge() {
        return age;
    }

    public void setAge(ObservableInt age) {
        this.age = age;

    }

    public ObservableArrayList<String> getHobby() {
        return hobby;
    }

    public void setHobby(ObservableArrayList<String> hobby) {
        this.hobby = hobby;
    }
}
