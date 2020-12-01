package com.example.hackathon2020binus.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExploreFragment extends Fragment {

    RecyclerView explore_rv_listUMKM;
    EditText explore_et_search;
    ArrayList<Umkm> listUmkm;
    ExploreAdapter exploreAdapter;
    FirebaseFirestore db;

    private void init(View view){
        explore_rv_listUMKM = view.findViewById(R.id.explore_rv_listUMKM);
        explore_et_search = view.findViewById(R.id.explore_et_search);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        init(view);
       // Log.d("ArraySize ", ": " + listUmkm.size());
        fetchData();

        return view;
    }



    private void fetchData(){
        listUmkm = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

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
                    exploreAdapter = new ExploreAdapter(getActivity(), listUmkm);
                    explore_rv_listUMKM.setAdapter(exploreAdapter);
                }else{
                    Log.d("Error fetchdata :" , "This is error message!");
                }

            }
        });

    }


}
