package com.example.hackathon2020binus.Tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hackathon2020binus.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartnershipTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartnershipTab extends Fragment {



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
        return inflater.inflate(R.layout.fragment_partnership_tab, container, false);
    }
}