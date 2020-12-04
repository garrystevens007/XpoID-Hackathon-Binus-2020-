package com.example.hackathon2020binus.Tab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.Fragment.FragmentController;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.Umkm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class FranchiseTab extends Fragment {

    ExploreAdapter exploreAdapter;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    RecyclerView tabFranchise_rv_savedFranchiseRv;
    ArrayList<Umkm> listUmkm;
    int count;

    public FranchiseTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_franchise_tab, container, false);
        init(view);
        // Inflate the layout for this fragment

        tabFranchise_rv_savedFranchiseRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    private void init(View view){
        db = FirebaseFirestore.getInstance();
        tabFranchise_rv_savedFranchiseRv = view.findViewById(R.id.TabFranchise_rv_savedFranchiseRv);
        listUmkm = new ArrayList<>();
        Log.d("hey",FirebaseStorage.historyOpenFranchise.toString());
        count = 0;
        for(String umkms : FirebaseStorage.historyOpenFranchise){
            DocumentReference documentReference = db.collection("listUMKM").document(umkms);
            count++;
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
                    listUmkm.add(umkm);
                    if(count == FirebaseStorage.historyOpenFranchise.size()){
                        ExploreAdapter exploreAdapter = new ExploreAdapter(getActivity(),listUmkm);
                        tabFranchise_rv_savedFranchiseRv.setAdapter(exploreAdapter);
                    }
                }
            });
        }



    }
}