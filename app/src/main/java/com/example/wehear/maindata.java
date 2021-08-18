package com.example.wehear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class maindata extends AppCompatActivity {
RecyclerView rcl1;
SimpleArcLoader sl1;
FirebaseDatabase db2;

ArrayList<ModelUserClass> ml1=new ArrayList<>();
HashMap<String,String> hashMap=new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindata);
Toast.makeText(this,"PLS TURN ON YOUR INTERNER IF ITS OFF",Toast.LENGTH_LONG).show();
        new AlertDialog.Builder(this)
                .setTitle("IMPORTANT!")
                .setMessage("NOT FOUND means the person with this number is login but this person is not store in your contact List")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.cancel();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.

                .show();
        rcl1=(RecyclerView)findViewById(R.id.allthedata);
        sl1=(SimpleArcLoader)findViewById(R.id.loader);
        db2= FirebaseDatabase.getInstance();
        Adapteruser au1=new Adapteruser(ml1,this);
        LinearLayoutManager line=new LinearLayoutManager(this);
        rcl1.setLayoutManager(line);
        rcl1.setAdapter(au1);

        getcontactdetails();
        db2.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ml1.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    //here we direct fetch the users whole data
                    ModelUserClass users=dataSnapshot.getValue(ModelUserClass.class);


                    if(hashMap.containsKey(users.getPhoneno().substring(3)))
                    {
                        users.setName(hashMap.get(users.getPhoneno().substring(3)));
                        Log.i("data1",""+users.getName());
                    }
                    if(hashMap.containsKey(users.getPhoneno()))
                    {
                        users.setName(hashMap.get(users.getPhoneno()));
                        Log.i("data1",""+users.getName());
                    }
                    users.setUserid(dataSnapshot.getKey());
                   //content provider code which says that i have the data in contact list then we have to change the name of the user accordingly


                        ml1.add(users);
                }
                au1.notifyDataSetChanged();
                sl1.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void getcontactdetails(){



        ContentResolver sl1=this.getContentResolver();
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor=sl1.query(uri,null,null,null,null);
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext()){

                Log.i("numbers",cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s","").replaceAll("-",""));
                Log.i("numbers",cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                hashMap.put(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s","").replaceAll("-",""),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));




            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}