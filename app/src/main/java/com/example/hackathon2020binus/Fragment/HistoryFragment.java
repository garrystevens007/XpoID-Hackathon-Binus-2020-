package com.example.hackathon2020binus.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.Adapter.HistoryPageAdapter;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.Tab.FranchiseTab;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

import static com.example.hackathon2020binus.Tab.FranchiseTab.tabFranchise_rv_savedFranchiseRv;
import static com.example.hackathon2020binus.Tab.PartnershipTab.tabPartnership_rv_savedPartnership;

public class HistoryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    HistoryPageAdapter pageAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tabLayout = view.findViewById(R.id.history_tabLayout);
        viewPager = view.findViewById(R.id.history_viewPager);
        pageAdapter = new HistoryPageAdapter(getChildFragmentManager(),getContext());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pageAdapter);
    }
}
