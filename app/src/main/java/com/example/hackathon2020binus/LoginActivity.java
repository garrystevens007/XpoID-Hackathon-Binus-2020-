package com.example.hackathon2020binus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText login_et_email,login_et_password;
    Button login_btn_signIn,login_btn_signUp,login_btn_continueWithGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        login_et_email = findViewById(R.id.login_et_email);
        login_et_password = findViewById(R.id.login_et_password);
        login_btn_signIn = findViewById(R.id.login_btn_signIn);
        login_btn_signUp = findViewById(R.id.login_btn_signUp);
        login_btn_continueWithGoogle = findViewById(R.id.login_btn_continueWithGoogle);
    }
}