package com.example.shiraz.medicalreport;

import android.content.Intent;
import android.icu.lang.UCharacterEnums;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Report extends AppCompatActivity {

    FirebaseDatabase database;

EditText ar_info;
Button ar_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__report);
        ar_info=(EditText)findViewById(R.id.ar_info);
        ar_submit=(Button)findViewById(R.id.ar_submit);


        if(Patient_Login.patient_info==null){return;}

        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(""+Patient_Login.patient_info.getName()+"");

        ar_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myRef.push().setValue(new String(""+ar_info.getText()+""));
                Toast.makeText(getApplicationContext(),"report Saved",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Add_Report.this,Patient_Page.class));



            }
        });


    }
}
