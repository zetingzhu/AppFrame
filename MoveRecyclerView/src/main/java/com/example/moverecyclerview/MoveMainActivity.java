package com.example.moverecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MoveMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_move);
    }

    public void showListView(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
    }

    public void showGridView(View view) {
        startActivity(new Intent(this, GridViewActivity.class));
    }
}
