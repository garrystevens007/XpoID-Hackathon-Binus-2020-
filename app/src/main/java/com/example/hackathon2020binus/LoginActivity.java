package com.example.hackathon2020binus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    final private int RC_SIGN_IN = 0;
    FirebaseAuth firebaseAuth;
    EditText login_et_email,login_et_password;
    Button login_btn_signIn,login_btn_signUp,login_btn_continueWithGoogle;
    GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog mLoadingBar;

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
        firebaseAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(LoginActivity.this);

        login_btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                String validateEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                email = login_et_email.getText().toString().trim();
                password = login_et_password.getText().toString().trim();

                if(email.equals("")){
                    login_et_email.setError("Email is required");
                    return;
                }else{
                    if(!email.matches(validateEmail)){
                        login_et_email.setError("Wrong format !");
                        return;
                    }
                }
                if(password.equals("")){
                    login_et_password.setError("Password required !");
                    return;
                }else{
                    if(password.length() < 6){
                        login_et_password.setError("Password >= 6");
                        return;
                    }
                }
                Log.d("Email", "onClick: " + email);
                Log.d("Password", "onClick: " + password);

                mLoadingBar.setTitle("Loging in");
                mLoadingBar.setMessage("Please wait, while check your credentials");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();
                inputDataFirebase(email,password);
            }
        });
        login_btn_continueWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        gso();
    }

    public void gso(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void inputDataFirebase(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            mLoadingBar.setTitle("Loging in");
            mLoadingBar.setMessage("Please wait, while check your credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error meesage : ", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}