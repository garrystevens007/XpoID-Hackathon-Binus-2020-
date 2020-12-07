package com.example.hackathon2020binus.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hackathon2020binus.MainActivity;
import com.example.hackathon2020binus.R;
import com.example.hackathon2020binus.Storage.FirebaseStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    Button accountFr_btn_signOut;
    TextView accountFr_tv_username;
    TextView accountFr_tv_email;
    FirebaseAuth firebaseAuth;

    void init(View view){
        accountFr_btn_signOut = view.findViewById(R.id.accountFr_btn_signOut);
        accountFr_tv_username = view.findViewById(R.id.accountFr_tv_username);
        accountFr_tv_email = view.findViewById(R.id.accountFr_tv_email);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        init(view);
        accountFr_tv_username.setText(FirebaseStorage.currUser);
        accountFr_tv_email.setText(FirebaseStorage.currUserEmail);

        accountFr_btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();

            }
        });

        return view;
    }
}
