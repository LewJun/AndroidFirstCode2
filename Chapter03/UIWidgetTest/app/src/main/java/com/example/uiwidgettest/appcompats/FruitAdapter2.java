package com.example.uiwidgettest.appcompats;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LewJun on 2018/1/7.
 */
public class FruitAdapter2 extends RecyclerView.Adapter<FruitAdapter2.ViewHolder> {
    private Context mContext;
    private int resource;
    private List<Fruit> fruits = new ArrayList<>();

    public FruitAdapter2(int resource, List<Fruit> fruits) {
        this.resource = resource;
        this.fruits = fruits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.tv_fruit_name.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImgId()).into(holder.iv_fruit_pic);
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView iv_fruit_pic;
        TextView tv_fruit_name;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            iv_fruit_pic = itemView.findViewById(R.id.iv_fruit_pic);
            tv_fruit_name = itemView.findViewById(R.id.tv_fruit_name);
/*
            Context context = itemView.getContext();
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Toast.makeText(context, "itemView clicked, " + position, Toast.LENGTH_SHORT).show();
            });

            iv_fruit_pic.setOnClickListener(v -> {
                Toast.makeText(context, "iv_fruit_pic clicked", Toast.LENGTH_SHORT).show();
            });

            tv_fruit_name.setOnClickListener(v -> {
                Toast.makeText(context, "tv_fruit_name clicked", Toast.LENGTH_SHORT).show();
            });*/
        }
    }
}
