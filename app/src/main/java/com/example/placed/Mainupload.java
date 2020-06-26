package com.example.placed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Mainupload extends AppCompatActivity {
    EditText name;
    Button upload;
   StorageReference storageReference;
   DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainupload);
        name=(EditText)findViewById(R.id.name);
        upload=(Button)findViewById(R.id.upload);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("placed");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();
            }
        });

    }

    private void selectPDFFile() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select PDF file"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK
        && data !=null && data.getData() !=null)
        {

           uploadPDFFile(data.getData());

        }
    }

    private void uploadPDFFile(Uri data) {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog= ProgressDialog.show(Mainupload.this,"LOADING","COMING",
                true);
        progressDialog.show();
        StorageReference reference=storageReference.child("placed"+System.currentTimeMillis()+".pdf");
        final ProgressDialog finalProgressDialog = progressDialog;
        final ProgressDialog finalProgressDialog1 = progressDialog;
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url=uri.getResult();
                uploadPDF uploadPDF=new uploadPDF(name.getText().toString(),url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                Toast.makeText(Mainupload.this, "File uploaded", Toast.LENGTH_SHORT).show();
                finalProgressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                finalProgressDialog1.setMessage("uploaded:"+(int)progress+"%");
            }
        });
    }
}
