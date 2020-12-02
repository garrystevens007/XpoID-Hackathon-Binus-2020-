package com.example.hackathon2020binus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.model.PromotionItems;
import com.example.hackathon2020binus.model.Umkm;

import java.util.ArrayList;

public class HomeFrPromotionAdapter extends RecyclerView.Adapter<HomeFrPromotionAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<PromotionItems> promotionItems;

    public HomeFrPromotionAdapter(Context context,ArrayList<PromotionItems> promotionItems){
        this.context = context;
        this.promotionItems = promotionItems;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_promotion,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PromotionItems promo = promotionItems.get(position);
        Glide.with(context).load(promo.getImgUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return promotionItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.promotion_img_promotionImage);
        }
    }
}
