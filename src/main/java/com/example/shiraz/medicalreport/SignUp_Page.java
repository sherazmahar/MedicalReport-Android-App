package com.example.shiraz.medicalreport;

import android.content.Intent;
import android.icu.lang.UCharacterEnums;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.*;
public class SignUp_Page extends AppCompatActivity {





    EditText su_name,su_email,su_age,su_passwor;
    RadioGroup su_type;
     boolean doctor=false;
     Button su_signup;





    FirebaseDatabase database;

    ArrayList<Patient_Data> patient_list;
    ArrayList<Doctor_Data> doctor_list;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__page);
        su_name=(EditText)findViewById(R.id.su_name);
        su_email=(EditText)findViewById(R.id.su_email);
        su_passwor=(EditText)findViewById(R.id.su_password);
        su_age=(EditText)findViewById(R.id.su_age);
        su_signup=(Button)findViewById(R.id.su_signup);

        su_type=(RadioGroup)findViewById(R.id.su_type);



        database = FirebaseDatabase.getInstance();
        patient_list=new ArrayList<>();
        doctor_list=new ArrayList<>();

        final DatabaseReference pt = database.getReference("Patient");
      final   DatabaseReference dr = database.getReference("Doctor");

        pt.addChildEventListener(new ChildEventListener() {
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
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Doctor_Data dr=dataSnapshot.getValue(Doctor_Data.class);
                doctor_list.add(dr);

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







        su_signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view)
    {


        Pattern p1=Pattern.compile("^[a-z][a-z0-9_/.]*@(gmail|yahoo|hotmail).com$");


        Matcher m1=p1.matcher(su_email.getText().toString());


        if(!m1.matches()){Toast.makeText(getApplicationContext(),"Email is not valid",Toast.LENGTH_SHORT).show(); return;}
        if(su_passwor.getText().length()<8){Toast.makeText(getApplicationContext(),"Password is not valid min 8 character",Toast.LENGTH_SHORT).show(); return;}




        if(doctor==false)
        {
       //     DatabaseReference myRef = database.getReference("Patient");

            for(int i=0;i<patient_list.size();i++)
            {
                String name=patient_list.get(i).getName().toString();
                if(name.equals(su_name.getText().toString())){Toast.makeText(getApplicationContext(),"Username Already Exits",Toast.LENGTH_SHORT).show();return;}

            }


            for(int i=0;i<patient_list.size();i++)
            {
                String email=patient_list.get(i).getEmail().toString();
                if(email.equals(su_email.getText().toString())){Toast.makeText(getApplicationContext(),"Email Already Exits",Toast.LENGTH_SHORT).show();return;}

            }

            pt.push().setValue(new Patient_Data(su_name.getText().toString(), su_email.getText().toString(), su_passwor.getText().toString(), Integer.parseInt(su_age.getText().toString()), 0));

            Toast.makeText(getApplicationContext(),"Patient SignUp Successfull",Toast.LENGTH_SHORT).show();
            startActivity(new Intent( SignUp_Page.this,MainOption.class));
        }
        else if(doctor==true)
        {
            for(int i=0;i<doctor_list.size();i++)
            {
                String email=doctor_list.get(i).getEmail().toString();
                if(email.equals(su_email.getText().toString())){Toast.makeText(getApplicationContext(),"Email Already Exits",Toast.LENGTH_SHORT).show();return;}

            }

           // DatabaseReference myRef = database.getReference("Doctor");
            dr.push().setValue(new Doctor_Data(su_name.getText().toString(), su_email.getText().toString(), su_passwor.getText().toString()));
            Toast.makeText(getApplicationContext(),"Doctor SignUp Successfull",Toast.LENGTH_SHORT).show();
            startActivity(new Intent( SignUp_Page.this,MainOption.class));


        }
    }
});




    su_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            if(i==R.id.su_dr)
            {
                Toast.makeText(getApplicationContext(),"Doctor Selected",Toast.LENGTH_SHORT).show();
                su_age.setVisibility(View.GONE);


                doctor=true;

            }
            else if(i==R.id.su_pt)
            {
                Toast.makeText(getApplicationContext(),"Patient selcted",Toast.LENGTH_SHORT).show();
                su_age.setVisibility(View.VISIBLE);
                doctor=false;


            }


        }
    });



    }
}
