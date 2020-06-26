package com.example.placed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class student extends AppCompatActivity {

        ListView mylistview;
        DatabaseReference databaseReference;
        List<uploadPDF> uploadPDFS;
        ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        mylistview = findViewById(R.id.mylistview);

        uploadPDFS = new ArrayList<>();
        checkNetworkConnectionStatus();



        //progressDialog= ProgressDialog.show(student.this,"LOADING BRO PLESE WAIT","COMING",true);


        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uploadPDF uploadPDF=uploadPDFS.get(position);
                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                Uri uri=Uri.parse(uploadPDF.getUrl());
                if(uri.toString().contains(".pdf"))
                {
                    intent.setDataAndType(uri,"application/pdf");
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });


    }

    private void checkNetworkConnectionStatus() {
        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){ //connected with either mobile or wifi
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected){ //wifi connected
                viewallfiles();
            }
            else if (mobileConnected){ //mobile data connected
                viewallfiles();

            }
        }
        else { //no internet connection
            Toast.makeText(student.this, "check your internet connection", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(student.this,MainActivity.class);
            finishAffinity();
            startActivity(intent);


        }
    }

    private void viewallfiles() {
            databaseReference = FirebaseDatabase.getInstance().getReference("placed");
            progressDialog= ProgressDialog.show(student.this,"LOADING BRO PLESE WAIT(INTERNET CONNECTION IS NEEDED)","COMING",true);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   // progressDialog= ProgressDialog.show(student.this,"LOADING BRO PLESE WAIT","COMING",true);
                    for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                        uploadPDF uploadPDF = postsnapshot.getValue(com.example.placed.uploadPDF.class);
                        uploadPDFS.add(uploadPDF);
                    }
                    String[] upload = new String[uploadPDFS.size()];
                    for (int i = 0; i < upload.length; i++) {
                        upload[i] = uploadPDFS.get(i).getName();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, upload)
                    {
                      public View getView(int position, View convertview, ViewGroup parent)
                      {
                          View view=super.getView(position,convertview,parent);
                          TextView tv=(TextView) view.findViewById(android.R.id.text1);
                          tv.setTextColor(Color.rgb(199,21,33));
                          return view;
                      }

                    };
                    mylistview.setAdapter(adapter);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(student.this, "check your database connection", Toast.LENGTH_LONG).show();

                }
            });

    }

}