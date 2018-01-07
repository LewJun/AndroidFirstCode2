package com.example.uiwidgettest.appcompats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LewJun on 2018/1/7.
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private int resource;
    private List<Fruit> fruits = new ArrayList<>();

    public FruitAdapter(int resource, List<Fruit> fruits) {
        this.resource = resource;
        this.fruits = fruits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
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

            Context context = itemView.getContext();
            itemView.setOnClickListener(v -> {
                Toast.makeText(context, "itemView clicked", Toast.LENGTH_SHORT).show();
            });

            iv_fruit_pic.setOnClickListener(v -> {
                Toast.makeText(context, "iv_fruit_pic clicked", Toast.LENGTH_SHORT).show();
            });

            tv_fruit_name.setOnClickListener(v -> {
                Toast.makeText(context, "tv_fruit_name clicked", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
