package frame.zzt.com.appframe.dialogFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import frame.zzt.com.appframe.R;

public class DialogFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);

        MyDialogFragment.showDialog(getSupportFragmentManager());

    }
}