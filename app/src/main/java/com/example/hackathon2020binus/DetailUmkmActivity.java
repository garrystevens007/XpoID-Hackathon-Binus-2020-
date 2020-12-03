package com.example.hackathon2020binus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hackathon2020binus.Fragment.ExploreFragment;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.hackathon2020binus.Util.Constants.MAPVIEW_BUNDLE_KEY;

public class DetailUmkmActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView detailActivity_imgView_imgBisnis;
    Button detailActivity_btn_back, detailActivity_btn_franchise, detailActivity_btn_partnership, detailActivity_btn_contact;
    TextView detailActivity_tv_title, detailActivity_tv_year, detailActivity_tv_description,
            detailActivity_tv_omzet;
    MapView bisnisMap;
    RecyclerView detailActivity_rv_productImg;
    private FirebaseFirestore db;

    Double lat;
    Double lng;
    String nama="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_umkm);
        bisnisMap = findViewById(R.id.detailActivity_bisnis_map);

        Intent intent = getIntent();
        init(intent);
        initGoogleMap(savedInstanceState);

    }

    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        bisnisMap.onCreate(mapViewBundle);

        bisnisMap.getMapAsync(this);
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
        detailActivity_btn_contact = findViewById(R.id.detailActivity_btn_contact);

        final Umkm listUmkm = (Umkm) intent.getParcelableExtra("selectedUmkm");
        Glide.with(DetailUmkmActivity.this).load(listUmkm.getGambar())
                .into(detailActivity_imgView_imgBisnis);
        detailActivity_tv_title.setText(listUmkm.getNama());
        detailActivity_tv_description.setText(listUmkm.getDeksripsi());
        Log.v("openFranchise",listUmkm.getOpenToFranchise()+"");
        Log.v("openPartnership",listUmkm.getOpenToPartnership()+"");

        lat = listUmkm.getLatitude();
        lng = listUmkm.getLongitude();
        nama = listUmkm.getNama();

        if(listUmkm.getOpenToFranchise()==true){
            detailActivity_btn_franchise.setEnabled(true);

        }else{
            detailActivity_btn_franchise.setEnabled(false);
        }

        if(listUmkm.getOpenToPartnership()) {
            detailActivity_btn_partnership.setEnabled(true);
        }else{
            detailActivity_btn_partnership.setEnabled(false);
        }

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

        detailActivity_btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_INSERT);
                i.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                i.putExtra(ContactsContract.Intents.Insert.NAME, listUmkm.getNama());
                i.putExtra(ContactsContract.Intents.Insert.PHONE, listUmkm.getPhone());
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivity(i);
                }else{
                    Toast.makeText(DetailUmkmActivity.this,"There is no app that support this action!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateData(){
        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        bisnisMap.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        bisnisMap.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        bisnisMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        bisnisMap.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(nama));
    }

    @Override
    public void onPause() {
        bisnisMap.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        bisnisMap.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        bisnisMap.onLowMemory();
    }

}