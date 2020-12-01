package com.example.hackathon2020binus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.model.Umkm;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Umkm> mUmkmList;

    public ExploreAdapter(Context context, ArrayList<Umkm> mUmkmList){
        this.mContext = context;
        this.mUmkmList = mUmkmList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.rectanglecustomview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Umkm umkm = mUmkmList.get(position);
        holder.rectangle_tv_title.setText(umkm.getNama());
        holder.rectangle_tv_desc.setText(umkm.getDeksripsi());
    }

    @Override
    public int getItemCount() {
        return mUmkmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView rectangle_tv_title, rectangle_tv_desc;
        ImageView rectangle_img_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rectangle_tv_title = itemView.findViewById(R.id.rectangle_tv_title);
            rectangle_tv_desc = itemView.findViewById(R.id.rectangle_tv_desc);
            rectangle_img_logo = itemView.findViewById(R.id.rectangle_img_logo);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
