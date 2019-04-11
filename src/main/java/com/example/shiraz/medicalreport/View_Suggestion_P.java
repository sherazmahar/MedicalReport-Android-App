package com.example.shiraz.medicalreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class View_Suggestion_P extends AppCompatActivity {

    FirebaseDatabase database;
    LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__suggestion__p);
        list=(LinearLayout)findViewById(R.id.vs_list);


        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("s_"+Patient_Login.patient_info.getName());
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                TextView tv=new TextView(View_Suggestion_P.this);
                tv.setText(dataSnapshot.getValue(String.class));
                tv.setTextSize(20);
                list.addView(tv);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

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
}
