package com.sgap.exam.NotifyAlarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.TextView;

public class IntentMessage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView tView = new TextView(this);
		tView.setText("알림 메시지입니다.");
		setContentView(tView);
		
		// Notify 삭제
        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancel(12346);
	}
}
