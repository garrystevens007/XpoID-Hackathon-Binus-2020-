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

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Umkm> mUmkmList;

    public SavedAdapter(Context ctx, ArrayList<Umkm> mUmkmList){
        this.ctx = ctx;
        this.mUmkmList = mUmkmList;
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View v = layoutInflater.inflate(R.layout.rectanglecustomview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {
        final Umkm umkm = mUmkmList.get(position);
        holder.rectangle_tv_title.setText(umkm.getNama());
        holder.rectangle_tv_desc.setText(umkm.getDeksripsi());
        Glide.with(ctx).load(umkm.getGambar()).into(holder.rectangle_img_logo);
    }

    @Override
    public int getItemCount() {
        return mUmkmList.size();
    }

    public void savedList(ArrayList<Umkm> savedList){
        mUmkmList = savedList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView rectangle_tv_title, rectangle_tv_desc;
        ImageView rectangle_img_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rectangle_tv_title = itemView.findViewById(R.id.rectangle_tv_title);
            rectangle_tv_desc = itemView.findViewById(R.id.rectangle_tv_desc);
            rectangle_img_logo = itemView.findViewById(R.id.rectangle_img_logo);
        }
    }

}
