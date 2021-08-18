package com.example.wehear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.regex.*;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
EditText ed1;
Button b1;
FirebaseAuth auth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.
                PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},0);
        }
        auth2=FirebaseAuth.getInstance();
        ed1=(EditText)findViewById(R.id.phonenumber);
        b1=(Button)findViewById(R.id.otpid);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Pattern p1 = Pattern.compile("^\\d{10}$");
            Matcher m=p1.matcher(ed1.getText().toString());
            if(m.matches()) {
                Intent i1 = new Intent(MainActivity.this, manageotp.class);
                i1.putExtra("Mobile","+91"+ed1.getText().toString());
                startActivity(i1);
            }
            else
            {
                Toast.makeText(MainActivity.this,"Enter valid Number(Indian)",Toast.LENGTH_LONG).show();
            }

            }
        });
        if(auth2.getCurrentUser()!=null)
        {
            Intent i1=new Intent(MainActivity.this,maindata.class);
            startActivity(i1);
        }


    }

}