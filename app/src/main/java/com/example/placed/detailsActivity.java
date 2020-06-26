package com.example.placed;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class detailsActivity extends AppCompatActivity {

    TextView students1,students2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        students1=findViewById(R.id.students1);
        students2=findViewById(R.id.students2);
        String first="(1)"+" "+"Hellow this app is showing you the current placement details of engineering collges." +" "+
                "in the case of student section student can see the current placement details.";

        String second="(2)"+"The teachers section they can directly put their college placement details in a pdf format after the otp verification."+
                 "if the students have any placemenet details of their colleges they can also add.";
        students1.setText(first);
        students2.setText(second);

    }
}
