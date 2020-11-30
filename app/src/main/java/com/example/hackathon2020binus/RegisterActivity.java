package com.example.hackathon2020binus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    Button register_btn_back, register_btn_signup, register_btn_google, register_btn_login;
    EditText register_et_email, register_et_fullname, register_et_phonenumber, register_et_pass, register_et_confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        register_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to login
                
            }
        });

        register_btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to google
            }
        });

        register_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_et_email.getText().toString().trim(),
                        fullname = register_et_fullname.getText().toString().trim()
                        , phonenumber = register_et_phonenumber.getText().toString().trim(),
                        pass = register_et_pass.getText().toString().trim()
                        , confirmpass = register_et_confirmpass.getText().toString().trim();

                if(email.isEmpty() || fullname.isEmpty()||phonenumber.isEmpty()||pass.isEmpty()||confirmpass.isEmpty()){
                    //alert here
                }
            }
        });

    }

    void init(){
        register_btn_back = findViewById(R.id.register_btn_back);
        register_btn_google = findViewById(R.id.register_btn_google);
        register_btn_signup = findViewById(R.id.register_btn_signup);
        register_btn_login = findViewById(R.id.register_btn_login);
        register_et_email = findViewById(R.id.register_et_email);
        register_et_fullname = findViewById(R.id.register_et_fullname);
        register_et_phonenumber = findViewById(R.id.register_et_phonenumber);
        register_et_pass = findViewById(R.id.register_et_pass);
        register_et_confirmpass = findViewById(R.id.register_et_confirmpass);
    }
}