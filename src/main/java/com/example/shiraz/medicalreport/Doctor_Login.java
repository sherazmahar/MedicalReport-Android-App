package com.example.shiraz.medicalreport;

import android.content.Intent;
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

public class Doctor_Login extends AppCompatActivity {

    TextView dr_create_ac;

    FirebaseDatabase database;


    ArrayList<Doctor_Data> doctor_list;

    EditText dl_email,dl_password;
    Button dl_login;

    public  static Doctor_Data doctor_info;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(Doctor_Login.this);
        database = FirebaseDatabase.getInstance(); // THIS LINE IS CRASHING
        DatabaseReference myRef = database.getReference("Doctor");


        setContentView(R.layout.activity_doctor__login);
        dr_create_ac=(TextView)findViewById(R.id.dr_create_ac);
        dl_email=(EditText)findViewById(R.id.dl_email);
        dl_password=(EditText)findViewById(R.id.dl_password);
        dl_login=(Button)findViewById(R.id.dl_login);


        dl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=0;i<doctor_list.size();i++)
                {
                    String a1=doctor_list.get(i).getEmail();
                    String a11=dl_email.getText().toString();

                    String b1=doctor_list.get(i).getPassword();
                    String b11=dl_password.getText().toString();


                    if(a1.equals(a11) && b1.equals(b11))
                    {
                        Toast.makeText(getApplicationContext(),""+doctor_list.get(i).getName()+"",Toast.LENGTH_SHORT).show();
                        doctor_info=doctor_list.get(i);
                        startActivity(new Intent(Doctor_Login.this,Doctor_Page.class));

                    }

                }


                Toast.makeText(getApplicationContext(),"Invalid Login Data",Toast.LENGTH_SHORT).show();



            }
        });


        //database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("Doctor");

        doctor_list=new ArrayList<>();


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Doctor_Data dr=dataSnapshot.getValue(Doctor_Data.class);
                doctor_list.add(dr);
                int x=10;



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
                startActivity(new Intent(Doctor_Login.this,SignUp_Page.class));
            }
        });
    }
}
