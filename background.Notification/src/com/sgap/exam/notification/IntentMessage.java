package com.sgap.exam.notification;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class IntentMessage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView tView = new TextView(this);
		tView.setText("알림 메시지입니다.");
		setContentView(tView);
	}
}
