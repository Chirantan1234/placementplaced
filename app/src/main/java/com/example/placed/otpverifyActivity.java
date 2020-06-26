package com.example.placed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class otpverifyActivity extends AppCompatActivity {
    private EditText editTextMobile;
    private Button buttoncontinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        editTextMobile = findViewById(R.id.editTextMobile);
        buttoncontinue=findViewById(R.id.buttonContinue);
        buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString();
                Intent intent = new Intent(otpverifyActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });



    }

}
