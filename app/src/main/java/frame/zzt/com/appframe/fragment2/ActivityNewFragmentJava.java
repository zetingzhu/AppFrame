package frame.zzt.com.appframe.fragment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import frame.zzt.com.appframe.R;

/**
 * @author: zeting
 * @date: 2020/7/20
 */
public class ActivityNewFragmentJava extends AppCompatActivity {
//    private ActivityNewFragmentBinding binding;

    static class MyFactory extends FragmentFactory {

        @NonNull
        @Override
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
            if (className.equals(FragmentNew.class.getName())) {
                return new FragmentNew();
            }
            return super.instantiate(classLoader, className);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getSupportFragmentManager().setFragmentFactory(new MyFactory());
        super.onCreate(savedInstanceState);

//        binding = ActivityNewFragmentBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_new_fragment);

        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentNew1 fragment1 = new FragmentNew1();
        fragmentTransaction.add(R.id.fcv_view_1, fragment1);
        fragmentTransaction.commit();

        FragmentNew2 fragment2 = new FragmentNew2();
        fragmentTransaction.add(R.id.fcv_view_2, fragment2);
        fragmentTransaction.commit();
    }


}
