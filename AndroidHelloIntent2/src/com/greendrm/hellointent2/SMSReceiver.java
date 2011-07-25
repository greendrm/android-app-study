package com.greendrm.hellointent2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			Toast.makeText(context, "Received SMS", Toast.LENGTH_LONG).show();
			Log.d("Receiver", "Received SMS");
		}
	}
}
