package com.example.wehear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class manageotp extends AppCompatActivity {
EditText ed2;
Button b2;
String phonenumber;
String otpid;
FirebaseDatabase db;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageotp);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        phonenumber=getIntent().getStringExtra("Mobile").toString();
        ed2=(EditText)findViewById(R.id.otpnumber);

        b2=(Button) findViewById(R.id.manageotpforsignin);

        // now do phone number sign in
        //now do when your app generate successfull otp
        intiatotp();

        //
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed2.getText().toString().isEmpty())
                {
                    Toast.makeText(manageotp.this,"Pls Enter OTP",Toast.LENGTH_SHORT).show();

                }
                else if(ed2.getText().toString().length()!=6)
                    Toast.makeText(manageotp.this,"INVALID OTP",Toast.LENGTH_SHORT).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,ed2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });



    }

    private void intiatotp() {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        //when your sim is not in your device
                        otpid=s;

                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                       // Toast.makeText(manageotp.this,"OVERHERE",Toast.LENGTH_SHORT).show();
                             signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        e.printStackTrace();

                        Toast.makeText(manageotp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }).build();




        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(manageotp.this,"ModelClass Running",Toast.LENGTH_LONG).show();
                           ModelUserClass m1=new ModelUserClass("Not Found",phonenumber);
                           String uid=task.getResult().getUser().getUid();

                           m1.setUserid(uid);
                           db.getReference().child("Users").child(uid).setValue(m1, new DatabaseReference.CompletionListener() {
                               @Override
                               public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                   if(error!=null)
                                   Toast.makeText(manageotp.this,"Your Data Saved in Database",Toast.LENGTH_LONG).show();

                               }

                           });
                           startActivity(new Intent(manageotp.this,maindata.class));



                        } else {
                            Toast.makeText(manageotp.this,"Failed error Ocurred",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}