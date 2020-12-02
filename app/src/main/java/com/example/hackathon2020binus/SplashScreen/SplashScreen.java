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
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<Umkm> listUmkm;
    private boolean flag = true;
    private final int SPLASH_DELAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("Run","Masuk Bosku");
                    fetchData();
                    if(flag == true){
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        SplashScreen.this.startActivity(i);
                        SplashScreen.this.finish();
                    }
                }
            },SPLASH_DELAY_LENGTH);

        if(flag == false){
            Toast.makeText(SplashScreen.this,"Failed to retrive data!", Toast.LENGTH_SHORT).show();
        }

    }

    public void fetchData(){
        db = FirebaseFirestore.getInstance();
        listUmkm = new ArrayList<>();
        CollectionReference collectionReference = db.collection("listUMKM");
        Query umkmQuery = collectionReference;

        umkmQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    listUmkm.clear();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Umkm umkm = document.toObject(Umkm.class);
                        umkm.setNama(document.getData().get("nama").toString());
                        umkm.setDeksripsi(document.getData().get("deskripsi").toString());
                        umkm.setGambar(document.getData().get("gambar").toString());
                        umkm.setId(document.getData().get("id").toString());
                        umkm.setOpenToFranchise((Boolean)document.getData().get("openToFranchise"));
                        umkm.setOpenToPartnership((Boolean)document.getData().get("openToPartnerShip"));
                        umkm.setOwnerName(document.getData().get("ownerName").toString());

                        listUmkm.add(umkm);
                        Log.d("Size","onComplete: " + listUmkm.size());
                    }
                    flag = true;
                }else{
                    flag = false;
                    Log.d("Error fetchdata :" , "This is error message!");
                }
            }
        });
    }

}