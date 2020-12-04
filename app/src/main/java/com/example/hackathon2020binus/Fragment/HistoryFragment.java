package com.example.hackathon2020binus.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hackathon2020binus.Adapter.HistoryPageAdapter;
import com.example.hackathon2020binus.R;
import com.google.android.material.tabs.TabLayout;

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
        pageAdapter = new HistoryPageAdapter(getFragmentManager());

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pageAdapter);
    }
}
