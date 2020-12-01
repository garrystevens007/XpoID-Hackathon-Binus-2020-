package com.example.hackathon2020binus.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArrayFireStore {
    public static ArrayList<Umkm> listUmkm = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("listUMKM");

    private DocumentSnapshot mDocumentSnapshot;

    public void readListUMKM(Context context){
        Query umkmQuery;

        if(mDocumentSnapshot != null){
            umkmQuery = collectionReference.startAfter(mDocumentSnapshot);
        }else{
            umkmQuery = collectionReference;
        }

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
                        umkm.setOpenToFranchise((Boolean) document.getData().get("openToFranchise"));
                        umkm.setOpenToPartnership((Boolean) document.getData().get("openToPartnerShip"));
                        umkm.setOwnerName(document.getData().get("ownerName").toString());

                        if(task.getResult().size() != 0){
                            mDocumentSnapshot = task.getResult().getDocuments().get(task.getResult().size()-1);
                        }
                        listUmkm.add(umkm);
                        Log.d("Size","onComplete: " + listUmkm.size());
                    }
                }else{
                    Log.d("Error fetchdata :" , "This is error message!");
                }
            }
        });

    }
}
