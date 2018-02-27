package com.example.droidstyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectorActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> mArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        listView = findViewById(R.id.list);
        getData();
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SelectorActivity.this, "Item Click on " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnSelected(View view) {
        Button btn = (Button) view;
        if (btn.isSelected()) {
            btn.setSelected(false);
            btn.setText("未选中");
        } else {
            btn.setSelected(true);
            btn.setText("已选中");
        }
    }

    public void btnActivate(View view) {
        Button btn = (Button) view;
        if (btn.isActivated()) {
            btn.setActivated(false);
            btn.setText("未激活");
        } else {
            btn.setActivated(true);
            btn.setText("已激活");
        }
    }

    private ArrayList<String> getData() {
        mArrayList.add("测试数据0");
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        return mArrayList;
    }

    class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                inflater = LayoutInflater.from(SelectorActivity.this);
                convertView = inflater.inflate(R.layout.item_list, parent, false);
                holder = new ViewHolder();
                holder.titleTxt = convertView.findViewById(R.id.txt_title);
                holder.button = convertView.findViewById(R.id.btn);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.titleTxt.setText(mArrayList.get(position));
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SelectorActivity.this, "Button " + position + " click", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView titleTxt;
            Button button;
        }
    }
}
