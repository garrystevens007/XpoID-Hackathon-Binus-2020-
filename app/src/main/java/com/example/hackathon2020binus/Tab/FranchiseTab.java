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

    public static ExploreAdapter exploreAdapter;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    public static RecyclerView tabFranchise_rv_savedFranchiseRv;
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
    public static void init(View view){
        tabFranchise_rv_savedFranchiseRv = view.findViewById(R.id.TabFranchise_rv_savedFranchiseRv);
        Log.d("tes",tabFranchise_rv_savedFranchiseRv.toString());
        exploreAdapter = new ExploreAdapter(FirebaseStorage.historyFranchise);
        tabFranchise_rv_savedFranchiseRv.setAdapter(exploreAdapter);
        exploreAdapter.notifyDataSetChanged();
    }

}