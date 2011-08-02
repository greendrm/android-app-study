package com.sgap.exam.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Timer;
import java.util.TimerTask;


public class NotifyActivity extends Activity {
	private static final int NOTI_ID = 12345;
	private Timer timer = new Timer();
	private int count=0;
	NotificationManager mNotiMgr;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mNotiMgr =
            (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            
		Button btn=(Button)findViewById(R.id.notify);
		
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				TimerTask task = new TimerTask() {
					public void run() {
					    notifyEvent();
					}
				};
		
				timer.schedule(task, 2000); // 2sec
			}
		});
		
		btn=(Button)findViewById(R.id.cancel);
		
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			    mNotiMgr.cancel(NOTI_ID);
			}
		});
	}
	
	private void notifyEvent() {
		Notification note = new Notification(R.drawable.s_ball,
		        "축구공을 받으세요~",
				System.currentTimeMillis());
		PendingIntent i = PendingIntent.getActivity(this, 0,
				new Intent(this, IntentMessage.class),
				0);
		
		note.setLatestEventInfo(this, "제목: 축구공 이벤트",
				"축구공 이벤트 알림입니다... 선택해주세요", i);
		note.number = ++count;
		
		mNotiMgr.notify(NOTI_ID, note);
	}
}