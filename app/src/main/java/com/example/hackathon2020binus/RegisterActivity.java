package com.example.hackathon2020binus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hackathon2020binus.Fragment.FragmentController;
import com.example.hackathon2020binus.model.Notifications;
import com.example.hackathon2020binus.model.Umkm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends LoginActivity {

    Button register_btn_back, register_btn_signup, register_btn_google, register_btn_login;
    EditText register_et_email, register_et_fullname, register_et_phonenumber, register_et_pass, register_et_confirmpass;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private String userID, email, fullname, phonenumber, pass, confirmpass;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLoadingBar = new ProgressDialog(RegisterActivity.this);
        init();

        register_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        register_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        register_btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signIn();
            }
        });

        register_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, " Entering register", Toast.LENGTH_SHORT).show();
                email = register_et_email.getText().toString().trim();
                fullname = register_et_fullname.getText().toString().trim();
                phonenumber = register_et_phonenumber.getText().toString().trim();
                pass = register_et_pass.getText().toString().trim();
                confirmpass = register_et_confirmpass.getText().toString().trim();
                String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(email.isEmpty() && fullname.isEmpty() && phonenumber.isEmpty() && pass.isEmpty() && confirmpass.isEmpty()){
                    register_et_fullname.setError("Fullname required!");
                    register_et_email.setError("Email required!");
                    register_et_phonenumber.setError("Phonenumber required!");
                    register_et_pass.setError("Password required!");
                    register_et_confirmpass.setError("Confirm password required!");
                    return;
                }
                else if(email.isEmpty() || fullname.isEmpty()|| phonenumber.isEmpty()|| pass.isEmpty()|| confirmpass.isEmpty()){
                    //alert here
//                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
//                    alert.setTitle("Login error !");
//                    alert.setMessage("You must fill all the boxes! ");
//                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    AlertDialog alertDialog = alert.create();
//                    alert.show();

                    if(fullname.isEmpty()){
                        register_et_fullname.setError("Fullname required!");
                        return;
                    }
                    if(email.isEmpty()){
                        register_et_email.setError("Email required!");
                        return;
                    }else{
                        if(!email.matches(regex)) {
                            register_et_email.setError("Invalid email format!");
                            return;
                        }
                    }
                    if(phonenumber.isEmpty()){
                        register_et_phonenumber.setError("Phonenumber required!");
                        return;
                    }
                    else {
                        if(phonenumber.length() != 12 || phonenumber.length() != 11){
                            register_et_phonenumber.setError("Phonenumber required!");
                            return;
                        }
                    }
                    if(pass.isEmpty()){
                        register_et_pass.setError("Password required!");
                        return;
                    } else {
                        if(pass.length() < 8) {
                            //alert here
                            register_et_pass.setError("Min 8 character");
                            return;
                        }
                    }

                    if(confirmpass.isEmpty()){
                        register_et_confirmpass.setError("Confirm password required!");
                        return;
                    } else {
                        if(confirmpass.equals(pass))Log.d("Pass","Pass!");
                        else{
                            register_et_confirmpass.setError("Password not same !");
                            return;//alert here
                        }
                    }
                }else {
                    if(!email.matches(regex)) {
                        register_et_email.setError("Invalid email format!");
                        return;
                    }
                    if(phonenumber.length() != 12 || phonenumber.length() != 11){
                        register_et_phonenumber.setError("Phonenumber required!");
                        return;
                    }
                    if(pass.length() < 8) {
                        //alert here
                        register_et_pass.setError("Min 8 character");
                        return;
                    }
                    if(confirmpass.equals(pass))Log.d("Pass","Pass!");
                    else{
                        register_et_confirmpass.setError("Password not same !");
                        return;//alert here
                    }

                    mLoadingBar.setTitle("Registering your account");
                    mLoadingBar.setMessage("Please wait, while check your credentials");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();
                }

//                if(confirmpass.length() < 8){
//                    //alert here
//                    register_et_confirmpass.setError("Min 8 character");
//                    return;
//                }

//                if(confirmpass.equals(pass))Log.d("Pass","Pass!");
//                else{
//                    register_et_confirmpass.setError("Password not same !");
//                    return;//alert here
//                }

                registerFirebase(email,pass);
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                Log.d("Firebase : ","is Created!");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void registerFirebase(final String email, String password){

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    ArrayList<Umkm> listUMKM = new ArrayList<>();
                    ArrayList<Notifications> listNotif = new ArrayList<>();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("connection",0);
                    user.put("email",email);
                    user.put("historyFranchise",listUMKM);
                    user.put("historyPartnership",listUMKM);
                    user.put("savedUMKM",listUMKM);
                    user.put("listNotif",listNotif);
                    user.put("name",fullname);
                    user.put("phone",phonenumber);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Firebase ", "onSuccess: user profile is created");
                        }
                    });
                    Toast.makeText(RegisterActivity.this, "Success Register", Toast.LENGTH_SHORT).show();
                    //intent goes here
                }else{
//                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    mLoadingBar.dismiss();
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
        gso();
    }
}