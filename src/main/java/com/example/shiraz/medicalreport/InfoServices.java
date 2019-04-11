package com.example.shiraz.medicalreport;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by usman on 19-Jun-18.
 */

public class InfoServices extends Service {




    private MediaPlayer player;
    public void notify11()
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        String name_number=pref.getString("info_name","xxx");
        String number=pref.getString("info_number","xxx");
        String adress=pref.getString("info_adress","xxx");
        String bloodgroup=pref.getString("info_bloodgroup","xxx");





        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.doctor) // notification icon
                .setContentTitle(name_number+" ("+number+" )") // title for notification
                .setContentText("BloodGroup : "+bloodgroup+"   "+adress) // message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(this, MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    void recursive_info()
    {
        new Handler().postDelayed(new Runnable() {
            public void run() {

                //  startActivity(new Intent(MainActivity.this, MainOption.class));
                //  finish();
                //  notify11();
                notify11();
                recursive_info();
            }
        }, 3000);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
        //player = MediaPlayer.create(this,
          ///      Settings.System.DEFAULT_RINGTONE_URI);
        //setting loop play to true
        //this will make the ringtone continuously playing
        //player.setLooping(true);

        //staring the player
        //player.start();
       recursive_info();

        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
        player.stop();
    }
}
