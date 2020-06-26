package com.example.placed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;

public class AdminActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    EditText adminusername,adminpassword;
    Button adminlogins;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        adminusername=findViewById(R.id.adminusername);
        adminpassword=findViewById(R.id.adminpassword);
        adminlogins=findViewById(R.id.adminlogin);
        databaseReference=FirebaseDatabase.getInstance().getReference("admin");

        adminlogins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(AdminActivity.this, "seeing", "loading", true);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (adminusername==null || adminpassword==null)
                        {
                            Toast.makeText(AdminActivity.this, "failed next", Toast.LENGTH_SHORT).show();
                        }
                        else{
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String email = String.valueOf(dataSnapshot1.child("email").getValue());
                            String password = String.valueOf(dataSnapshot1.child("password").getValue());
                            if (email.equals(adminusername.getText().toString()) && password.equals(adminpassword.getText().toString())) {
                                Intent i = new Intent(AdminActivity.this, Mainupload.class);
                                startActivity(i);
                                progressDialog.dismiss();
                            }
                            else if (!email.equals(adminusername.getText().toString()) && !password.equals(adminpassword.getText().toString()))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(AdminActivity.this, "check your details", Toast.LENGTH_LONG).show();
                            }


                        } }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
