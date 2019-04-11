package com.example.shiraz.medicalreport;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {





    static  int file_count1=0;

    static  int file_count2=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        startService(new Intent(getApplicationContext(), InfoServices.class));


        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(MainActivity.this, MainOption.class));
                finish();
                //  notify11();
                //  startService(new Intent(getApplicationContext(), InfoServices.class));
            }
        }, 3000);

    }

}
