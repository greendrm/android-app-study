package com.greendrm.hellointent2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends Activity {
	final String TAG="HELLOINTENT2/Second";
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "onStart()");
//		Toast.makeText(getApplicationContext(), "Sec/onPause", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d(TAG, "onRestart()");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG,"onResume()");
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause()");
//		Toast.makeText(getApplicationContext(), "Sec/onPause", Toast.LENGTH_SHORT).show();
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "onStop()");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroy()");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		setContentView(R.layout.second);
		
		Intent extra = getIntent();
		String val = extra.getStringExtra("PARAMS_SEC_VALUE");
		Toast.makeText(getApplicationContext(), 
				val, Toast.LENGTH_LONG).show();
		
        Button buttonIntent = (Button)findViewById(R.id.buttonIntent);
        buttonIntent.setOnClickListener(new View.OnClickListener() {
      	@Override
      	public void onClick(View v) {
    		Intent data = new Intent();
    		data.putExtra("RESULT_SEC_VALUE", "From Second Activity");
    		setResult(Activity.RESULT_OK, data);
    		finish();
      	}
      });

	}

}
