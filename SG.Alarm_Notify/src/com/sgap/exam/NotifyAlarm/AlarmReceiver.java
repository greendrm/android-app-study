package com.sgap.exam.NotifyAlarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager mNotiMgr;
    private Context mContext;
    private final int NOTI_ID = 12346;
    private int count=0;

    public void onReceiveIntent(Context context, Intent intent) {
        mContext = context;
        mNotiMgr = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notifyEvent();
        
//        Toast.makeText(context, "알람으로 Intent를 Receiver로 받습니다.", Toast.LENGTH_SHORT).show();
        abortBroadcast();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onReceiveIntent(context, intent);
    }


    private void notifyEvent() {
        Notification note=new Notification(R.drawable.s_ball,
                "축구공을 받으세요~",
                System.currentTimeMillis());
        PendingIntent intent = PendingIntent.getActivity( mContext, 
                1, new Intent( mContext, IntentMessage.class), 0);
        
        note.setLatestEventInfo( mContext, "제목: 축구공 이벤트",
                "축구공 이벤트 알림입니다... 선택해주세요", intent);
        note.number=++count;
        
        mNotiMgr.notify(NOTI_ID, note);
    }

}
