package com.example.hackathon2020binus.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
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

import static com.example.hackathon2020binus.Util.Constants.MAPVIEW_BUNDLE_KEY;

public class ExploreFragment extends Fragment {

    RecyclerView explore_rv_listUMKM;
    EditText explore_et_search;
    TextView explore_tv_result, explore_tv_error;
    ImageView explore_imgView_error;
    ArrayList<Umkm> listUmkm;
    ExploreAdapter exploreAdapter;
    FirebaseFirestore db;

    String realTimeText = "";
    char kutip = '"';


    private void init(View view){
        explore_rv_listUMKM = view.findViewById(R.id.explore_rv_listUMKM);
        explore_et_search = view.findViewById(R.id.explore_et_search);
        explore_tv_result = view.findViewById(R.id.explore_tv_result);
        explore_imgView_error = view.findViewById(R.id.explore_imgView_error);
        explore_tv_error = view.findViewById(R.id.explore_tv_error);
        listUmkm = FirebaseStorage.umkms;
        exploreAdapter = new ExploreAdapter(getActivity(), listUmkm);
        explore_rv_listUMKM.setAdapter(exploreAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        init(view);
        explore_et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                realTimeText = explore_et_search.getText().toString();
                explore_tv_result.setText(kutip+realTimeText+kutip);
                if(realTimeText.equals("")){
                    explore_tv_result.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        
        explore_rv_listUMKM.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    private void filter(String key){
        ArrayList<Umkm> filterUmkm = new ArrayList<>();
        for(Umkm item :  FirebaseStorage.umkms){
            if(item.getNama().toLowerCase().contains(key.toLowerCase())){
                filterUmkm.add(item);
            }
        }

        if(filterUmkm.size() == 0){
            explore_imgView_error.setVisibility(View.VISIBLE);
            explore_tv_error.setVisibility(View.VISIBLE);
        }else {
            explore_imgView_error.setVisibility(View.INVISIBLE);
            explore_tv_error.setVisibility(View.INVISIBLE);
        }

        exploreAdapter.filterList(filterUmkm);
    }

}
