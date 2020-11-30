package com.example.hackathon2020binus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button register_btn_back, register_btn_signup, register_btn_google, register_btn_login;
    EditText register_et_email, register_et_fullname, register_et_phonenumber, register_et_pass, register_et_confirmpass;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private String userID, email, fullname, phonenumber, pass, confirmpass;
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
                email = register_et_email.getText().toString().trim();
                fullname = register_et_fullname.getText().toString().trim();
                phonenumber = register_et_phonenumber.getText().toString().trim();
                pass = register_et_pass.getText().toString().trim();
                confirmpass = register_et_confirmpass.getText().toString().trim();
                String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(email.isEmpty() || fullname.isEmpty()||phonenumber.isEmpty()||pass.isEmpty()||confirmpass.isEmpty()){
                    //alert here
                    return;
                }

                if(email.matches(regex)) {
                    Log.d("Email","Pass!");
                }
                else {
                    Log.d("Email","Does not pass!");
                    return;//alert here
                }

                if(pass.length() < 8) {
                    //alert here
                    return;
                }

                if(confirmpass.length() < 8){
                    //alert here
                    return;
                }

                if(confirmpass.equals(pass))Log.d("Pass","Pass!");
                else return;//alert here


                registerFirebase(email,pass);
                Log.d("Firebase : ","is Created!");
            }
        });

    }

    private void registerFirebase(final String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    userID = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("email",email);
                    user.put("historyFranchise",0);
                    user.put("historyPartnership",0);
                    user.put("listNotif",0);
                    user.put("name",fullname);
                    user.put("phone",phonenumber);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Firebase ", "onSuccess: user profile is created " + userID);
                        }
                    });
                    Toast.makeText(RegisterActivity.this, "Success Register", Toast.LENGTH_SHORT).show();
                    //intent goes here
                }else{
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
}