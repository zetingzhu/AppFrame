package frame.zzt.com.appframe.singlelist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.trade.eight.moudle.initDialog.InitDialogUtil;

import frame.zzt.com.appframe.R;

/**
 * @author: zeting
 * @date: 2020/5/27
 */
public class ActivitySingleList extends AppCompatActivity {
    boolean[] mCheckedItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signle_layout);

        findViewById(R.id.btn_test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedItem = 1;
                String[] choices = new String[]{"1", "22", "333", "4444", "55555"};

//                ArrayAdapter arrayAdapter1 = new ArrayAdapter<String>(ActivitySingleList.this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, choices) {};
//                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ActivitySingleList.this, R.layout.dialog_single_list_layout_item, R.id.text1, choices) {
//                };
//
//                new AlertDialog.Builder(ActivitySingleList.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
//                        .setSingleChoiceItems(arrayAdapter, checkedItem, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//
//                            }
//                        }).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        Log.e("111111111111", "" + position);
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                })
//                        .show();


                /****************************************************************/


                setSingleDialog(ActivitySingleList.this, choices, checkedItem, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });


            }
        });


        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        InitDialogUtil.Companion.getInstance()
                .requestInitDialogType(ActivitySingleList.this, 0,
                        null, recyclerview);

    }


    public void setSingleDialog(Context context, final String[] mCheckedItems, final int checkedItem, final AdapterView.OnItemClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_single_list_layout);

        final ListView lv = (ListView) dialog.findViewById(R.id.dialog_single_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
            }
        });


        lv.setAdapter(new ArrayAdapter<String>(context, R.layout.dialog_single_list_layout_item, R.id.text1, mCheckedItems) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final View view = super.getView(position, convertView, parent);
                final RadioButton rb = view.findViewById(R.id.radio);
                if (mCheckedItems != null) {
                    if (position == checkedItem) {
                        lv.setItemChecked(position, true);
                    }
                }
                return view;
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

}
