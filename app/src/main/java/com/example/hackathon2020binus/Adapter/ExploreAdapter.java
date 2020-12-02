package com.example.hackathon2020binus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.model.Umkm;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Umkm> mUmkmList;
    private ArrayList<Umkm> fullList;
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
        Glide.with(mContext).load(umkm.getGambar()).into(holder.rectangle_img_logo);
    }

    @Override
    public int getItemCount() {
        return mUmkmList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Umkm> listUmkm = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                listUmkm.addAll(fullList);
            }else{
                String key = constraint.toString().toLowerCase().trim();

                for(Umkm item: fullList){
                    if(item.getNama().toLowerCase().contains(key)){
                        listUmkm.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listUmkm;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mUmkmList.clear();
            mUmkmList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public ExploreAdapter(ArrayList<Umkm> listUmkm){
        this.mUmkmList = listUmkm;
        fullList = new ArrayList<>(mUmkmList);
    }

    public void filterList(ArrayList<Umkm> filteredList){
        mUmkmList = filteredList;
        notifyDataSetChanged();
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
