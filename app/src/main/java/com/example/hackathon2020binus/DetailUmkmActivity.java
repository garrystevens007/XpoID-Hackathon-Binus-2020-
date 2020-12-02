package com.example.hackathon2020binus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hackathon2020binus.Fragment.ExploreFragment;
import com.example.hackathon2020binus.model.Umkm;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailUmkmActivity extends AppCompatActivity {

    ImageView detailActivity_imgView_imgBisnis;
    Button detailActivity_btn_back, detailActivity_btn_franchise, detailActivity_btn_partnership;
    TextView detailActivity_tv_title, detailActivity_tv_year, detailActivity_tv_description,
            detailActivity_tv_omzet;
    RecyclerView detailActivity_rv_productImg;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_umkm);

        Intent intent = getIntent();
        init(intent);

    }

    private void init(Intent intent){
        db = FirebaseFirestore.getInstance();
        detailActivity_imgView_imgBisnis = findViewById(R.id.detailActivity_imgView_imgBisnis);
        detailActivity_btn_franchise = findViewById(R.id.detailActivity_btn_franchise);
        detailActivity_btn_partnership = findViewById(R.id.detailActivity_btn_partnership);
        detailActivity_btn_back = findViewById(R.id.detailActivity_btn_back);
        detailActivity_tv_title = findViewById(R.id.detailActivity_tv_title);
        detailActivity_tv_year = findViewById(R.id.detailActivity_tv_year);
        detailActivity_tv_description = findViewById(R.id.detailActivity_tv_description);
        detailActivity_tv_omzet = findViewById(R.id.detailActivity_tv_omzet);
        detailActivity_rv_productImg = findViewById(R.id.detailActivity_rv_productImg);

        final Umkm listUmkm = intent.getParcelableExtra("selectedUmkm");
        Glide.with(DetailUmkmActivity.this).load(listUmkm.getGambar())
                .into(detailActivity_imgView_imgBisnis);
        detailActivity_tv_title.setText(listUmkm.getNama());
        detailActivity_tv_description.setText(listUmkm.getDeksripsi());

        detailActivity_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        detailActivity_btn_franchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        detailActivity_btn_partnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void updateData(){
        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}