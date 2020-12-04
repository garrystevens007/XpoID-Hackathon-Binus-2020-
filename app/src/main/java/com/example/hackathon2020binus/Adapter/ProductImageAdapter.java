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
import com.example.hackathon2020binus.model.Umkm;

import java.util.ArrayList;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String>  mProductImage;

    public ProductImageAdapter(Context mContext, ArrayList<String> mproductImage){
        this.mContext = mContext;
        this.mProductImage = mproductImage;
    }

    @NonNull
    @Override
    public ProductImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_product_image,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageAdapter.ViewHolder holder, int position) {
        final String productImage = mProductImage.get(position);
        Glide.with(mContext).load(productImage).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mProductImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_img);
        }
    }
}
