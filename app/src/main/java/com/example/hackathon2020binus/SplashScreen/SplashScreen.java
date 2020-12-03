package com.example.hackathon2020binus.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.MainActivity;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.PromotionItems;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    FirebaseFirestore db;
    private boolean flag ;
    private final int SPLASH_DELAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchData();
                }
            },SPLASH_DELAY_LENGTH);
    }

    public void fetchData(){
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("listUMKM");
        Query umkmQuery = collectionReference;
        Log.d("tes1" , ""+db+"   "+collectionReference);


        umkmQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    FirebaseStorage.umkms.clear();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Umkm umkm = document.toObject(Umkm.class);
                        umkm.setNama(document.getData().get("nama").toString());
                        umkm.setDeksripsi(document.getData().get("deskripsi").toString());
                        umkm.setGambar(document.getData().get("gambar").toString());
                        umkm.setId(document.getData().get("id").toString());
                        umkm.setOpenToFranchise(Boolean.parseBoolean(document.getData().get("openToFranchise").toString()));
                        umkm.setOpenToPartnership(Boolean.parseBoolean(document.getData().get("openToPartnership").toString()));
                        umkm.setOwnerName(document.getData().get("ownerName").toString());
                        umkm.setPhone(document.getData().get("phone").toString());

                        FirebaseStorage.umkms.add(umkm);
                    }

                    CollectionReference collectionReference1 = db.collection("promotions");
                    Query promoQuery = collectionReference1;

                    promoQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                FirebaseStorage.promoItems.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    PromotionItems items = document.toObject(PromotionItems.class);
                                    items.setImgUrl(document.getData().get("gambar").toString());
                                    FirebaseStorage.promoItems.add(items);
                                }
                            }
                        }
                    });

                    if(FirebaseStorage.umkms.isEmpty()){
                        flag = false;
                        Toast.makeText(SplashScreen.this,"Failed to retrive data!\nNo Internet Connection", Toast.LENGTH_SHORT).show();
                    }else{
                        flag = true;
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        SplashScreen.this.startActivity(i);
                        SplashScreen.this.finish();
                    }
                }
            }
        });
    }

}