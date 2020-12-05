package com.example.hackathon2020binus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hackathon2020binus.Adapter.ProductImageAdapter;
import com.example.hackathon2020binus.Fragment.ExploreFragment;
import com.example.hackathon2020binus.Fragment.FragmentController;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.hackathon2020binus.Util.Constants.MAPVIEW_BUNDLE_KEY;

public class DetailUmkmActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView detailActivity_imgView_imgBisnis;
    Button detailActivity_btn_back, detailActivity_btn_franchise, detailActivity_btn_partnership, detailActivity_btn_contact, detailActivity_btn_unsave, detailActivity_btn_save;
    TextView detailActivity_tv_title, detailActivity_tv_year, detailActivity_tv_description,
            detailActivity_tv_omzet;
    MapView bisnisMap;
    RecyclerView detailActivity_rv_productImg;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    ArrayList<String> productImage;
    ProductImageAdapter productImageAdapter;
    Double lat;
    Double lng;
    String nama="";
    boolean bool = false;
    Integer index = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_umkm);
        bisnisMap = findViewById(R.id.detailActivity_bisnis_map);

        Intent intent = getIntent();
        init(intent);
        initGoogleMap(savedInstanceState);

        detailActivity_rv_productImg.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

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
        firebaseAuth = FirebaseAuth.getInstance();
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
        detailActivity_btn_unsave = findViewById(R.id.detailActivity_btn_unsave);

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
        productImage = listUmkm.getProductImage();

        productImageAdapter = new ProductImageAdapter(this,productImage);
        detailActivity_rv_productImg.setAdapter(productImageAdapter);

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

        for(int i = 0 ; i < FirebaseStorage.savedUMKM.size();i++){
            if(FirebaseStorage.savedUMKM.get(i).equals(listUmkm.getId())){
                index = i;
                bool = false;
                if(bool == false){
                    detailActivity_btn_unsave.setBackgroundResource(R.drawable.icon_btn_saved);
                }
                break;
            }
        }


        detailActivity_btn_unsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool = true;
                index = null;
                for(int i = 0 ; i < FirebaseStorage.savedUMKM.size();i++){
                    if(FirebaseStorage.savedUMKM.get(i).equals(listUmkm.getId())){
                        index = i;
                        bool = false;
                        break;
                    }
                }
                if(bool == true){
                    DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                    documentReference.update("savedUMKM", FieldValue.arrayUnion(listUmkm.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailUmkmActivity.this,"Successfully added to saved",Toast.LENGTH_SHORT).show();
//                            Drawable saved = AppCompatResources.getDrawable(DetailUmkmActivity.this,R.drawable.icon_btn_unsaved);
//                            Drawable wrappedDrawable = DrawableCompat.wrap(saved);
//                            DrawableCompat.setTint(wrappedDrawable, Color.BLACK);

                            detailActivity_btn_unsave.setBackgroundResource(R.drawable.icon_btn_saved);
                        }
                    });
                }else{

                    DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                    documentReference.update("savedUMKM",FieldValue.arrayRemove(listUmkm.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            FirebaseStorage.savedUMKM.remove(index);
                            FirebaseStorage.savedListUMKM.remove(index);
                            Toast.makeText(DetailUmkmActivity.this,"Remove index " + index,Toast.LENGTH_SHORT).show();

                            detailActivity_btn_unsave.setBackgroundResource(R.drawable.icon_btn_unsaved);

                        }
                    });

                }
            }
        });

        detailActivity_btn_franchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                documentReference.update("historyFranchise", FieldValue.arrayUnion(listUmkm.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailUmkmActivity.this,"Successfully ask to franchise!",Toast.LENGTH_SHORT).show();
                        getInfo();
                    }
                });
            }
        });

        detailActivity_btn_partnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tes woi","partnership tapped");
                DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                documentReference.update("historyPartnership", FieldValue.arrayUnion(listUmkm.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailUmkmActivity.this,"Successfully ask to partnership!",Toast.LENGTH_SHORT).show();
                        getInfo();
                    }
                });

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
    private void getInfo() {
        String currID = firebaseAuth.getCurrentUser().getUid();
        Log.d("HomeFragment", "Current User " + currID);
        DocumentReference documentReference = db.collection("users").document(currID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d("Fragment Controller", "Snapshot : " + value.getString("name"));
                FirebaseStorage.historyOpenFranchise = (ArrayList<String>) value.get("historyFranchise");
                FirebaseStorage.historyOpenPartnership = (ArrayList<String>) value.get("historyPartnership");
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