package com.example.shiraz.medicalreport;

import android.content.Intent;
import android.icu.lang.UCharacterEnums;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Patient_Login extends AppCompatActivity {

    TextView dr_create_ac;
    EditText pl_email,pl_password;
    Button pl_login;

    public  static Patient_Data patient_info;


   FirebaseDatabase database;

    ArrayList<Patient_Data> patient_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(Patient_Login.this);
        database = FirebaseDatabase.getInstance(); // THIS LINE IS CRASHING
        DatabaseReference myRef = database.getReference("Patient");

        setContentView(R.layout.activity_patient__login);
        dr_create_ac=(TextView)findViewById(R.id.pt_create_ac);
        pl_email=(EditText)findViewById(R.id.pl_email);
        pl_password=(EditText)findViewById(R.id.pl_password);
        pl_login=(Button)findViewById(R.id.pl_login);

        pl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                for (int i=0;i<patient_list.size();i++)
                {
                    String a1=patient_list.get(i).getEmail();
                    String a11=pl_email.getText().toString();

                    String b1=patient_list.get(i).getPassword();
                    String b11=pl_password.getText().toString();


                    if(a1.equals(a11) && b1.equals(b11))
                    {
                        Toast.makeText(getApplicationContext(),""+patient_list.get(i).getName()+"",Toast.LENGTH_SHORT).show();
                        patient_info=patient_list.get(i);
                        startActivity(new Intent(Patient_Login.this,Patient_Page.class));

                         return;
                    }

                }

                Toast.makeText(getApplicationContext(),"Invalid Login Data",Toast.LENGTH_SHORT).show();



            }
        });


          //database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("Patient");

        patient_list=new ArrayList<>();


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Patient_Data pd=dataSnapshot.getValue(Patient_Data.class);
                patient_list.add(pd);



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        dr_create_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_Login.this,SignUp_Page.class));
            }
        });
    }
}
