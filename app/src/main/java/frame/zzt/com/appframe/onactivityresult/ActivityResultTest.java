package frame.zzt.com.appframe.onactivityresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2020/11/2
 * <p>
 * 还没有找到方法，
 * onActivityResult 现状？
 * Google 可能也意识到onActivityResult的这些问题，
 * 在androidx.activity:activity:1.2.0-alpha02和androidx.fragment:fragment:1.3.0-alpha02 中，
 * 已经废弃了startActivityForResult和onActivityResult方法。
 */
public class ActivityResultTest extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
