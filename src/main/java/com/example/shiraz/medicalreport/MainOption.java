package com.example.shiraz.medicalreport;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcode.permissionslib.main.OnRequestPermissionsCallBack;
import com.kcode.permissionslib.main.PermissionCompat;

public class MainOption extends AppCompatActivity {


    ImageView mo_doctor,mo_patient;


    @Override
    public void onBackPressed() {
      //  finish();
    }

    void get_permision()
    {
        PermissionCompat.Builder bbuilder = new PermissionCompat.Builder(getBaseContext());


        bbuilder.addPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA});
        bbuilder.addPermissionRationale("say why need the permission");
        bbuilder.addRequestPermissionsCallBack(new OnRequestPermissionsCallBack() {
            @Override
            public void onGrant()
            {
                //do something
                //   Write_File();

            }

            @Override
            public void onDenied(String permission) {
                // Log.e(TAG, permission + "Denied");
            }
        });




        //bbuilder.build().request();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_option);
        mo_doctor=(ImageView)findViewById(R.id.mo_doctor);
        mo_patient=(ImageView)findViewById(R.id.mo_patient);

        mo_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainOption.this,Doctor_Login.class));
            }
        });
        mo_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainOption.this,Patient_Login.class));
            }
        });


        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions

            //get_permision();
        } else{
            // do something for phones running an SDK before lollipop
        }




    }
}
