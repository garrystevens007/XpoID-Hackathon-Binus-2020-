package com.example.hackathon2020binus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.model.Umkm;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeFrBisnisBaruAdapter extends RecyclerView.Adapter<HomeFrBisnisBaruAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<String> mUmkmList;

    public HomeFrBisnisBaruAdapter(Context mContext, ArrayList<String> mUmkmList) {
        this.mContext = mContext;
        this.mUmkmList = mUmkmList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_new_business,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String umkm = mUmkmList.get(position);
        Glide.with(mContext).load(umkm).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUmkmList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.square_img_logo);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
