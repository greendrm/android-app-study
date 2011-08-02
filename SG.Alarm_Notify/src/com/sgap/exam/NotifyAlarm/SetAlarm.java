package com.sgap.exam.NotifyAlarm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SetAlarm extends Activity {

//    private NotificationManager nm;
//    private int YOURAPP_NOTIFICATION_ID;
    Toast mToast;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);

//        this.nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Button button = (Button) findViewById(R.id.set_alarm_button);
        button.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SetAlarm.this, AlarmReceiver.class);
                PendingIntent appIntent =
                	PendingIntent.getBroadcast(SetAlarm.this, 0, intent, 0);
                Calendar calendar = Calendar.getInstance();
                String tmp = calendar.getTime().toLocaleString();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 30);
                tmp += " ---> " + calendar.getTime().toLocaleString();
                Log.d("test", tmp);
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), appIntent);

//                showNotification(R.drawable.alarm, R.string.alarm_message, R.string.alarm_message, false);
            }
        } );

    }
/*
    private void showNotification(int statusBarIconID, int statusBarTextID, int detailedTextID, boolean showIconOnly) {

        Intent contentIntent = new Intent(this, SetAlarm.class);
        PendingIntent theappIntent = PendingIntent.getBroadcast(SetAlarm.this, 0, contentIntent, 0);
        CharSequence from = "Alarm Manager";
        CharSequence message = "The Alarm was fired";

        String tickerText = showIconOnly ? null : this.getString(statusBarTextID);
        Notification notif = new Notification(statusBarIconID, tickerText, System.currentTimeMillis());

        notif.setLatestEventInfo(this, from, message, theappIntent);

        this.nm.notify(this.YOURAPP_NOTIFICATION_ID, notif);
    }*/

}
