package com.example.hackathon2020binus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hackathon2020binus.Fragment.FragmentController;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button main_btn_login, main_btn_signup;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            Log.v("user",firebaseAuth.getCurrentUser().getEmail());
            startActivity(new Intent(getApplicationContext(), FragmentController.class));
            finish();
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

    void init(){
        main_btn_login = findViewById(R.id.main_btn_login);
        main_btn_signup = findViewById(R.id.main_btn_signup);
    }
}