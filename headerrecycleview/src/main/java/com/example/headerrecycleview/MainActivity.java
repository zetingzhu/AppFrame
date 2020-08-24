package com.example.headerrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
/**
 * @author: zeting
 * @date: 2020/8/19
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void linearlayout(View view) {
        Intent intent = new Intent(this,LinearlayoutActivity.class);
        startActivity(intent);
    }

    public void gridlayout(View view) {
        Intent intent = new Intent(this,GridlayoutActivity.class);
        startActivity(intent);
    }
}
