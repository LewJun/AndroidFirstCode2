package com.example.uiwidgettest.appcompats;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LewJun on 2018/1/7.
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> fruits = new ArrayList<>();

    public FruitAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_list_item_1, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.iv_fruit_pic.setImageResource(fruit.getImgId());
        holder.tv_fruit_name.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_fruit_pic;
        TextView tv_fruit_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_fruit_pic = itemView.findViewById(R.id.iv_fruit_pic);
            tv_fruit_name = itemView.findViewById(R.id.tv_fruit_name);
        }
    }
}
