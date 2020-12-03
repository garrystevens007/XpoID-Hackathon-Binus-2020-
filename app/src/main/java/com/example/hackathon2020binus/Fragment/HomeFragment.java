package com.example.hackathon2020binus.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.Adapter.HomeFrBisnisBaruAdapter;
import com.example.hackathon2020binus.Adapter.HomeFrPromotionAdapter;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.PromotionItems;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    TextView homeFr_tv_user_name,homeFr_tv_total_franchise,homeFr_tv_total_partnerships,homeFr_tv_total_connection;
    ImageView circleImageView;
    RecyclerView homeFr_rv_bisnis_terbaru,homeFr_rv_promotion,homeFr_rv_rekomendasi_bisnis;
    ArrayList<Umkm> listUmkm;
    ArrayList<PromotionItems> promoItems;
    HomeFrBisnisBaruAdapter homeFrBisnisBaruAdapter;
    HomeFrPromotionAdapter homeFrPromotionAdapter;
    ExploreAdapter exploreAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        getInfo();
        homeFr_rv_bisnis_terbaru.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeFr_rv_rekomendasi_bisnis.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        homeFr_rv_promotion.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        return view;
    }

    //getnama masih error
    private void getInfo(){
//        String currID = firebaseAuth.getCurrentUser().getUid();
//        Log.d("HomeFragment","Current User " + currID);
//        DocumentReference documentReference = firebaseFirestore.collection("users").document(currID);
//        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                homeFr_tv_user_name.setText(firebaseAuth.getCurrentUser().getDisplayName());
//            }
//        });
//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    name = documentSnapshot.getString("users");
//                    homeFr_tv_user_name.setText(name);
//                    Log.d("HomeFragment","Current User name " + name);
//                }else{
//                    Log.d("Document", " : Does not exists!");
//                }
//            }
//        });
    }

    private void init(View view){
        homeFr_tv_user_name = view.findViewById(R.id.homeFr_tv_user_name);
        homeFr_tv_total_franchise = view.findViewById(R.id.homeFr_tv_total_franchise);
        homeFr_tv_total_partnerships = view.findViewById(R.id.homeFr_tv_total_partnerships);
        homeFr_tv_total_connection = view.findViewById(R.id.homeFr_tv_total_connection);
        homeFr_rv_bisnis_terbaru = view.findViewById(R.id.homeFr_rv_bisnis_terbaru);
        homeFr_rv_promotion = view.findViewById(R.id.homeFr_rv_promotion);
        homeFr_rv_rekomendasi_bisnis = view.findViewById(R.id.homeFr_rv_rekomendasi_bisnis);
        circleImageView = view.findViewById(R.id.circleImageView);
        listUmkm = FirebaseStorage.umkms;

        promoItems = FirebaseStorage.promoItems;
        homeFrBisnisBaruAdapter = new HomeFrBisnisBaruAdapter(getActivity(), listUmkm);
        homeFr_rv_bisnis_terbaru.setAdapter(homeFrBisnisBaruAdapter);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        exploreAdapter = new ExploreAdapter(getActivity(),listUmkm);
        homeFr_rv_rekomendasi_bisnis.setAdapter(exploreAdapter);

        homeFrPromotionAdapter = new HomeFrPromotionAdapter(getActivity(),promoItems);
        homeFr_rv_promotion.setAdapter(homeFrPromotionAdapter);
    }
}
