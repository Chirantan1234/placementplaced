package com.example.placed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button student,teacher,admin,details;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student=findViewById(R.id.student);
        teacher=findViewById(R.id.teacher);
        admin=findViewById(R.id.admin);
        details=findViewById(R.id.details);
        text=findViewById(R.id.text);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent students=new Intent(MainActivity.this,student.class);
                startActivity(students);
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teachers=new Intent(getApplicationContext(),otpverifyActivity.class);
                startActivity(teachers);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent students=new Intent(MainActivity.this,AdminActivity.class);
                startActivity(students);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details=new Intent(MainActivity.this,detailsActivity.class);
                startActivity(details);
            }
        });

    }
}

