package com.example.hackathon2020binus.Tab;

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


public class PartnershipTab extends Fragment {

    public static ExploreAdapter exploreAdapter;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    public static RecyclerView tabPartnership_rv_savedPartnership;
    ArrayList<Umkm> listUmkm;
    int count;

    public PartnershipTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partnership_tab, container, false);
        init(view);
        // Inflate the layout for this fragment

        tabPartnership_rv_savedPartnership.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    public static void init(View view){
        tabPartnership_rv_savedPartnership = view.findViewById(R.id.TabPartnership_rv_savedPartnership);
        exploreAdapter = new ExploreAdapter(FirebaseStorage.historyPartnership);
        tabPartnership_rv_savedPartnership.setAdapter(exploreAdapter);
        exploreAdapter.notifyDataSetChanged();
    }
}