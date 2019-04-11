package com.example.shiraz.medicalreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class Patient_Page extends AppCompatActivity {


    ImageView pp_add,pp_show,pp_viewsuggestion;
    Button pp_addinfo;

    FirebaseDatabase database;
    Button pp_signout;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__page);




        pp_add=(ImageView) findViewById(R.id.pp_addreport);
        pp_show=(ImageView) findViewById(R.id.pp_viewreports);
        pp_viewsuggestion=(ImageView)findViewById(R.id.pp_viewsuggestion);
        pp_signout=(Button)findViewById(R.id.pp_signout);
        pp_addinfo=(Button)findViewById(R.id.pp_addinfo);

        pp_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Patient_Page.this,MainOption.class));
            }
        });

        pp_viewsuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_Page.this,View_Suggestion_P.class));
            }
        });

        pp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                startActivity(new Intent(Patient_Page.this,Upload_Img_Activity.class));

            }
        });

        pp_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                startActivity(new Intent(Patient_Page.this,View_Report.class));

            }
        });

        pp_addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InfoData.class));

            }
        });


    }
}
