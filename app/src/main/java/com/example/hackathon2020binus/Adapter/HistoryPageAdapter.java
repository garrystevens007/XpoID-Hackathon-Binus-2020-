package com.example.hackathon2020binus.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.Tab.FranchiseTab;
import com.example.hackathon2020binus.Tab.PartnershipTab;
import com.example.hackathon2020binus.model.Umkm;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

import static com.example.hackathon2020binus.Tab.FranchiseTab.exploreAdapter;
import static com.example.hackathon2020binus.Tab.FranchiseTab.tabFranchise_rv_savedFranchiseRv;
import static com.example.hackathon2020binus.Tab.PartnershipTab.tabPartnership_rv_savedPartnership;

public class HistoryPageAdapter extends FragmentPagerAdapter {
    FirebaseFirestore db;
    ArrayList<Umkm> listUmkm;
    Context context;
    int countFranchise;
    int countPartnership;
    public HistoryPageAdapter(FragmentManager fm, Context context){


        super(fm);
        Log.v("Tes state", "dikirim dari History page Adapter2");
        init();
        this.context  = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.v("Tes state", "dikirim dari History page Adapter");
        switch (position){
            case 0:
                PartnershipTab partnershipTab = new PartnershipTab();
                return partnershipTab;
            case 1:
                FranchiseTab franchiseTab = new FranchiseTab();
                return franchiseTab;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Partnership";
            case 1:
                return "Franchise";
            default:
                return null;
        }

    }
    private void init(){
        db = FirebaseFirestore.getInstance();
        FirebaseStorage.historyPartnership = new ArrayList<>();
        countPartnership = 0;
        for(String umkms : FirebaseStorage.historyOpenPartnership){
            DocumentReference documentReference = db.collection("listUMKM").document(umkms);
            countPartnership++;
            Log.d("hey",umkms);
            ListenerRegistration registration = documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    Umkm umkm = new Umkm();
                    umkm.setNama(value.getData().get("nama").toString());
                    Log.d("hey nama",value.getData().get("nama").toString());
                    umkm.setDeksripsi(value.getData().get("deskripsi").toString());
                    umkm.setGambar(value.getData().get("gambar").toString());
                    umkm.setId(value.getData().get("id").toString());
                    umkm.setOpenToFranchise(Boolean.parseBoolean(value.getData().get("openToFranchise").toString()));
                    umkm.setOpenToPartnership(Boolean.parseBoolean(value.getData().get("openToPartnership").toString()));
                    umkm.setOwnerName(value.getData().get("ownerName").toString());
                    umkm.setPhone(value.getData().get("phone").toString());
                    umkm.setLatitude(Double.parseDouble(value.getData().get("latitude").toString()));
                    umkm.setLongitude(Double.parseDouble(value.getData().get("longitude").toString()));
                    FirebaseStorage.historyPartnership.add(umkm);
                    if(countPartnership == FirebaseStorage.historyOpenPartnership.size()){
                        PartnershipTab.exploreAdapter = new ExploreAdapter(context,FirebaseStorage.historyPartnership);
                        tabPartnership_rv_savedPartnership.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        tabPartnership_rv_savedPartnership.setAdapter(PartnershipTab.exploreAdapter);
                        PartnershipTab.exploreAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        FirebaseStorage.historyFranchise = new ArrayList<>();
        Log.d("hey",FirebaseStorage.historyOpenFranchise.toString());
        countFranchise = 0;
        for(String umkms : FirebaseStorage.historyOpenFranchise){
            DocumentReference documentReference = db.collection("listUMKM").document(umkms);
            countFranchise++;
            Log.d("hey",umkms);
            ListenerRegistration registration = documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    Umkm umkm = new Umkm();
                    umkm.setNama(value.getData().get("nama").toString());
                    Log.d("hey nama",value.getData().get("nama").toString());
                    umkm.setDeksripsi(value.getData().get("deskripsi").toString());
                    umkm.setGambar(value.getData().get("gambar").toString());
                    umkm.setId(value.getData().get("id").toString());
                    umkm.setOpenToFranchise(Boolean.parseBoolean(value.getData().get("openToFranchise").toString()));
                    umkm.setOpenToPartnership(Boolean.parseBoolean(value.getData().get("openToPartnership").toString()));
                    umkm.setOwnerName(value.getData().get("ownerName").toString());
                    umkm.setPhone(value.getData().get("phone").toString());
                    umkm.setLatitude(Double.parseDouble(value.getData().get("latitude").toString()));
                    umkm.setLongitude(Double.parseDouble(value.getData().get("longitude").toString()));
                    FirebaseStorage.historyFranchise.add(umkm);
                    if(countFranchise == FirebaseStorage.historyOpenFranchise.size()){
                        FranchiseTab.exploreAdapter = new ExploreAdapter(context,FirebaseStorage.historyFranchise);
                        tabFranchise_rv_savedFranchiseRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        tabFranchise_rv_savedFranchiseRv.setAdapter(FranchiseTab.exploreAdapter);
                        FranchiseTab.exploreAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        Log.v("Tes state", "dikirim dari Histroy Fragment");
    }
}
