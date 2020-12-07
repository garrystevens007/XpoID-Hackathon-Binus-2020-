package com.example.hackathon2020binus.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathon2020binus.Adapter.SavedAdapter;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SavedFragment extends Fragment {

    public static RecyclerView saved_rv_listUMKM;
    public static SavedAdapter savedAdapter;
    private ArrayList<Umkm> dipsList;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    private ArrayList<String> savedList;
    String currID;
    ImageView saved_imgView_error;
    TextView saved_tv_error;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved,container, false);
        init(view);
        saved_rv_listUMKM.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void init(View view){
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        saved_imgView_error = view.findViewById(R.id.saved_imgView_error);
        saved_tv_error = view.findViewById(R.id.saved_tv_error);
        saved_rv_listUMKM = view.findViewById(R.id.saved_rv_listUMKM);
        savedAdapter = new SavedAdapter(getActivity(), dipsList);
        savedList();
        saved_rv_listUMKM.setAdapter(savedAdapter);

    }

    private void savedList(){
        fetchUserData();
        ArrayList<Umkm> dipsList = new ArrayList<>();
        Log.d("Size array savedList ", " from firebaseStorage :" + FirebaseStorage.savedUMKM.size());
        for(int i = 0; i < FirebaseStorage.savedUMKM.size(); i++){
            for(Umkm item : FirebaseStorage.umkms){
                if(FirebaseStorage.savedUMKM.get(i).equals(item.getId())){
                    dipsList.add(item);
                    break;
                }
            }
        }
        //savedAdapter.savedList(FirebaseStorage.savedListUMKM);
        if(dipsList.size() == 0){
            saved_imgView_error.setVisibility(View.VISIBLE);
            saved_tv_error.setVisibility(View.VISIBLE);
        }else {
            saved_imgView_error.setVisibility(View.INVISIBLE);
            saved_tv_error.setVisibility(View.INVISIBLE);
        }
        savedAdapter.savedList(dipsList);
        Log.d("Size array savedList : ", " " + FirebaseStorage.savedListUMKM);
    }

    private void fetchUserData(){
        Log.d("MASUK","FETCH DATA BOS : ");
        currID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(currID);
        ListenerRegistration registration = documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                FirebaseStorage.savedUMKM = (ArrayList<String>) value.get("savedUMKM");
                Log.d("PRINT","LIST OF SAVED UMKM : " + FirebaseStorage.savedUMKM);
            }
        });
    }

}
