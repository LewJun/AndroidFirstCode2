package com.example.uiwidgettest.containers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.util.List;

/**
 * Created by LewJun on 2018/1/7.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {
    private static final String TAG = "FruitAdapter";

    private int resource;

    public FruitAdapter(@NonNull Context context, int resource, @NonNull List<Fruit> fruits) {
        super(context, resource, fruits);
        this.resource = resource;
    }

    /**
     * 在每个子项被滚动到屏幕上时被调用
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "pos: " + position + ", convertView: " + convertView);
/*//        加载子项布局 不用添加到父布局，因为如果添加了父布局，那么就不能添加到ListView中了。
        View view = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
//        从布局中得到控件
        ImageView iv_fruit_pic = view.findViewById(R.id.iv_fruit_pic);
        TextView tv_fruit_name = view.findViewById(R.id.tv_fruit_name);

//        设置控件值
        Fruit fruit = getItem(position);
        iv_fruit_pic.setImageResource(fruit.getImgId());
        tv_fruit_name.setText(fruit.getName());
*/
//        如上log打印结果中，只有pos为0时，convertView为空，其它时候都新建的convertView
//        优化
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
        } else {
            view = convertView;
        }
        //        从布局中得到控件
        ImageView iv_fruit_pic = view.findViewById(R.id.iv_fruit_pic);
        TextView tv_fruit_name = view.findViewById(R.id.tv_fruit_name);

//        设置控件值
        Fruit fruit = getItem(position);
        iv_fruit_pic.setImageResource(fruit.getImgId());
        tv_fruit_name.setText(fruit.getName());
        return view;
    }
}
