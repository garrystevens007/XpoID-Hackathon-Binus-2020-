package com.example.hackathon2020binus.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathon2020binus.Adapter.ExploreAdapter;
import com.example.hackathon2020binus.Adapter.HomeFrBisnisBaruAdapter;
import com.example.hackathon2020binus.Adapter.HomeFrPromotionAdapter;
import com.example.hackathon2020binus.LoginActivity;
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
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    TextView homeFr_tv_user_name,homeFr_tv_total_franchise,homeFr_tv_total_partnerships,homeFr_tv_total_connection;
    ImageView circleImageView;
    RecyclerView homeFr_rv_bisnis_terbaru,homeFr_rv_promotion,homeFr_rv_rekomendasi_bisnis;
    ArrayList<Umkm> listUmkm;
    Button homeFr_btn_notification;
    ArrayList<PromotionItems> promoItems;
    ArrayList<String> listNew;
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
        getStatistic();
        putToLocal();

        homeFr_tv_user_name.setText(FirebaseStorage.currUser);

        homeFr_btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Notifications");
                alert.setMessage("Feature coming soon!");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alert.create();
                alert.show();
            }
        });

        homeFr_rv_bisnis_terbaru.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeFr_rv_rekomendasi_bisnis.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        homeFr_rv_promotion.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        return view;
    }


    private void init(View view){
        homeFr_tv_user_name = view.findViewById(R.id.homeFr_tv_user_name);
        homeFr_tv_total_franchise = view.findViewById(R.id.homeFr_tv_total_franchise);
        homeFr_tv_total_partnerships = view.findViewById(R.id.homeFr_tv_total_partnerships);
        homeFr_tv_total_connection = view.findViewById(R.id.homeFr_tv_total_connection);
        homeFr_rv_bisnis_terbaru = view.findViewById(R.id.homeFr_rv_bisnis_terbaru);
        homeFr_rv_promotion = view.findViewById(R.id.homeFr_rv_promotion);
        homeFr_rv_rekomendasi_bisnis = view.findViewById(R.id.homeFr_rv_rekomendasi_bisnis);
       // homeFr_rv_rekomendasi_bisnis.setNestedScrollingEnabled(false);
        circleImageView = view.findViewById(R.id.circleImageView);
        homeFr_btn_notification = view.findViewById(R.id.homeFr_btn_notification);
        listUmkm = FirebaseStorage.umkms;

        promoItems = FirebaseStorage.promoItems;
        listNew = FirebaseStorage.listNewBusiness;
        homeFrBisnisBaruAdapter = new HomeFrBisnisBaruAdapter(getActivity(), listNew);
        homeFr_rv_bisnis_terbaru.setAdapter(homeFrBisnisBaruAdapter);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        exploreAdapter = new ExploreAdapter(getActivity(),listUmkm);
        homeFr_rv_rekomendasi_bisnis.setAdapter(exploreAdapter);

        homeFrPromotionAdapter = new HomeFrPromotionAdapter(getActivity(),promoItems);
        homeFr_rv_promotion.setAdapter(homeFrPromotionAdapter);
    }

    private void putToLocal(){
        //Toast.makeText(getActivity(),"Put local is called",Toast.LENGTH_SHORT).show();
        getStatistic();
        int localStatPart = FirebaseStorage.historyOpenFranchise.size(),
                localStatFran = FirebaseStorage.historyOpenPartnership.size(),
                localStatConn = FirebaseStorage.statConnection;
        homeFr_tv_total_franchise.setText(String.valueOf(localStatFran));
        homeFr_tv_total_partnerships.setText(String.valueOf(localStatPart));
        homeFr_tv_total_connection.setText(String.valueOf(localStatConn));
    }

    private void getStatistic(){
        String currID = firebaseAuth.getCurrentUser().getUid();
        Log.d("HomeFragment", "Current User " + currID);
        DocumentReference documentReference = firebaseFirestore.collection("users").document(currID);
        ListenerRegistration registration =  documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d("Fragment Controller", "Snapshot : " + value.getString("name"));
                FirebaseStorage.historyOpenFranchise = (ArrayList<String>) value.get("historyFranchise");
                FirebaseStorage.historyOpenPartnership = (ArrayList<String>) value.get("historyPartnership");
            }
        });
    }
}
