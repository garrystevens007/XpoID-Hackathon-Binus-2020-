package com.example.hackathon2020binus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hackathon2020binus.Fragment.FragmentController;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.Umkm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button main_btn_login, main_btn_signup;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            getInfo();
            Log.v("user",firebaseAuth.getCurrentUser().getEmail());
            return;
        }
        setContentView(R.layout.activity_main);

        init();

        main_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        main_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
    private void getInfo() {
        String currID = firebaseAuth.getCurrentUser().getUid();
//        Log.d("HomeFragment", "Current User " + currID);
        DocumentReference documentReference = firebaseFirestore.collection("users").document(currID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                Log.d("Fragment Controller", "Snapshot : " + value.getString("name"));
                FirebaseStorage.currUser = value.getString("name");
                FirebaseStorage.historyOpenFranchise = (ArrayList<String>) value.get("historyFranchise");
                FirebaseStorage.historyOpenPartnership = (ArrayList<String>) value.get("historyPartnership");
                FirebaseStorage.notifications = (ArrayList<String>) value.get("listNotif");
                FirebaseStorage.savedUMKM = (ArrayList<String>) value.get("savedUMKM");
                fetchSavedList();
                startActivity(new Intent(getApplicationContext(), FragmentController.class));
                finish();
            }
        });
    }

    private void fetchSavedList(){
//        Log.d("Size array savedList ", " from firebaseStorage :" + FirebaseStorage.savedUMKM.size());
        for(int i = 0; i < FirebaseStorage.savedUMKM.size(); i++){
            for(Umkm item : FirebaseStorage.umkms){
                if(FirebaseStorage.savedUMKM.get(i).equals(item.getId())){
                    FirebaseStorage.savedListUMKM.add(item);
                    break;
                }
            }
        }
    }

    void init(){
        main_btn_login = findViewById(R.id.main_btn_login);
        main_btn_signup = findViewById(R.id.main_btn_signup);
    }
}