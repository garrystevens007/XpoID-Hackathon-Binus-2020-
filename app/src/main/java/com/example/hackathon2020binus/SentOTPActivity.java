package com.example.hackathon2020binus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SentOTPActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_o_t_p);

        final EditText sentOTPActivity_et_inputMobile = findViewById(R.id.sentOTPActivity_et_inputMobile);
        final Button sentOTPActivity_btn_sentOTP = findViewById(R.id.sentOTPActivity_btn_sentOTP);

        sentOTPActivity_btn_sentOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sentOTPActivity_et_inputMobile.getText().toString().isEmpty()){
                    Toast.makeText(SentOTPActivity.this, "Masukan Nomor Handphone Anda!", Toast.LENGTH_SHORT).show();
                    return;
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62"+sentOTPActivity_et_inputMobile.getText().toString(),60, TimeUnit.SECONDS,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                            }
                        });

                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                intent.putExtra("mobile", sentOTPActivity_et_inputMobile.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }
}