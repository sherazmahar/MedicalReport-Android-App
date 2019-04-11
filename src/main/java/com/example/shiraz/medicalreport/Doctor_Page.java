package com.example.shiraz.medicalreport;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import javax.xml.namespace.NamespaceContext;

public class Doctor_Page extends AppCompatActivity {


    FirebaseDatabase database;

    EditText sp_name,sp_suggestion;
    Button sp_search,sp_signout,sp_add_suggestion;


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__page);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_signout);

        sp_name=(EditText)findViewById(R.id.sp_name);
        sp_search=(Button) findViewById(R.id.sp_search);
        sp_signout=(Button) findViewById(R.id.sp_signout);
        sp_suggestion=(EditText)findViewById(R.id.sp_suggestion);
        sp_add_suggestion=(Button)findViewById(R.id.sp_add_suggestion);

        sp_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent( Doctor_Page.this,MainOption.class));
            }
        });

        database= FirebaseDatabase.getInstance();
        sp_add_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 DatabaseReference mysuggestion = database.getReference("s_"+sp_name.getText().toString());
                 mysuggestion.push().setValue(new String(Doctor_Login.doctor_info.getName()+" : "+sp_suggestion.getText().toString()));
                 sp_suggestion.setText("");

            }
        });





        sp_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                View_Report_Doctor.link_suggestions=new ArrayList<>();





                database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference(sp_name.getText().toString());
                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        View_Report_Doctor.link_suggestions.add(new Link_Suggestion(dataSnapshot.getValue(String.class)));
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                startActivity(new Intent(Doctor_Page.this,View_Report_Doctor.class));

                            }
                        }, 1000);


                        /*
                        TextView tv=new TextView(Doctor_Page.this);
                        tv.setText(dataSnapshot.getValue(String.class));
                        tv.setTextSize(20);
                        sp_list.addView(tv);

*/



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






            }
        });


    }
}
