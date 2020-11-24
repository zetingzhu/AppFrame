package com.example.coordinatorlayoutsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainCorrdinatorActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    SparseArray<String> sparseArray = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);

        recyclerView = findViewById(R.id.recyclerView);

        for (int i = 0; i < 60; i++) {
            sparseArray.put(i, "v_" + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyItemDecoration decoration = new MyItemDecoration(this);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                TextView textView = new TextView(parent.getContext());
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.RED);
                return new MViewHolder(textView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MViewHolder mViewHolder = (MViewHolder) holder;
                ((TextView) mViewHolder.itemView).setText(sparseArray.get(position));
                if (position % 2 == 0) {
                    mViewHolder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    mViewHolder.itemView.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public int getItemCount() {
                return sparseArray.size();
            }
        });
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {

        private Context context;

        public MyItemDecoration(Context context) {
            this.context = context;
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                int pos = parent.getChildAdapterPosition(child);
                if (pos % 3 == 0) {
                    Paint paint = new Paint();
                    paint.setColor(Color.BLUE);
                    float left = child.getLeft() + 300;
                    float right = left + 500;
                    float top = child.getTop() + 50;
                    float bottom = top + 300;
                    c.drawRect(left, top, right, bottom, paint);
                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setStrokeWidth(12);
                    paint.setTextSize(30);
                    c.drawText("这个是第：" + pos, left + 20, top + 60, paint);
                }

                View view = new View(context);
                view.draw(c);
            }

        }
    }
}