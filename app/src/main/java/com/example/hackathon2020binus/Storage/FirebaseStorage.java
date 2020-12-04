package com.example.hackathon2020binus.Storage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.Adapter.HomeFrBisnisBaruAdapter;
import com.example.hackathon2020binus.Adapter.HomeFrPromotionAdapter;
import com.example.hackathon2020binus.model.Notifications;
import com.example.hackathon2020binus.model.PromotionItems;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirebaseStorage {
    public static ArrayList<String> notifications = new ArrayList<>();
    public static ArrayList<PromotionItems> promoItems = new ArrayList<>();
    public static ArrayList<String> historyOpenFranchise = new ArrayList<>();
    public static ArrayList<String> historyOpenPartnership = new ArrayList<>();
    public static ArrayList<String> savedUMKM = new ArrayList<>();
    public static ArrayList<Umkm> umkms = new ArrayList<>();
    public static String currUser;

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = db.collection("listUMKM");
        Query umkmQuery = collectionReference;

        umkmQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    umkms.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Umkm umkm = document.toObject(Umkm.class);
                        umkm.setNama(document.getData().get("nama").toString());
                        umkm.setDeksripsi(document.getData().get("deskripsi").toString());
                        umkm.setGambar(document.getData().get("gambar").toString());
                        umkm.setId(document.getData().get("id").toString());
                        umkm.setOpenToFranchise((Boolean) document.getData().get("openToFranchise"));
                        umkm.setOpenToPartnership((Boolean) document.getData().get("openToPartnerShip"));
                        umkm.setOwnerName(document.getData().get("ownerName").toString());
                        umkm.setLatitude((Double) document.getData().get("latitude"));
                        umkm.setLongitude((Double) document.getData().get("longitude"));


                        umkms.add(umkm);
                    }
                }
            }
        });

        CollectionReference collectionReference1 = db.collection("promotions");
        Query promoQuery = collectionReference1;

        promoQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    promoItems.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PromotionItems items = document.toObject(PromotionItems.class);
                        items.setImgUrl(document.getData().get("gambar").toString());

                        promoItems.add(items);
                    }
                }
            }
        });
    }
}
