package com.example.moverecyclerview;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moverecyclerview.recyembellish.DividerListItemDecoration;
import com.example.moverecyclerview.recyevent.OnRecyclerItemClickListener;
import com.example.moverecyclerview.recyevent.RecyItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<String> mStringList;
    MoveRecyclerAdapter mRecyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.recy);
        initRecy();
    }

    private void initRecy() {
        if (mStringList == null) {
            mStringList = new ArrayList<>();
        }
        mStringList.addAll(DataManager.getData(15 - mStringList.size()));
        mRecyAdapter = new MoveRecyclerAdapter(R.layout.item_listview, mStringList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);

        RecyItemTouchHelperCallback itemTouchHelperCallback = new RecyItemTouchHelperCallback(mRecyAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                MoveRecyclerAdapter.ViewHolder viewHolder1 = (MoveRecyclerAdapter.ViewHolder) viewHolder;
                String tvString = viewHolder1.mTextView.getText().toString();
                Toast.makeText(ListViewActivity.this, "逗逗~" + tvString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(ListViewActivity.this, "" + "讨厌，不要老是摸人家啦...", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mRecyAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
