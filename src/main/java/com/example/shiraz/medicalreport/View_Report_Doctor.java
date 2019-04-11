package com.example.shiraz.medicalreport;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class View_Report_Doctor extends AppCompatActivity {


    FirebaseDatabase database;
    static ArrayList<Link_Suggestion> link_suggestions;
    ListView lv;

    ImageListAdapter list_adapter;


    @Override
    public void onBackPressed() {


        super.onBackPressed();

        finish();
        startActivity(new Intent(View_Report_Doctor.this, Doctor_Page.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__report__doctor);
        lv = (ListView) findViewById(R.id.view_report_lv_doctor);


        list_adapter = new ImageListAdapter(getApplicationContext(), link_suggestions);
        lv.setAdapter(list_adapter);


    }
}
