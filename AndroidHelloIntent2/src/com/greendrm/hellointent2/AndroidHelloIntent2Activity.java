package com.greendrm.hellointent2;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AndroidHelloIntent2Activity extends Activity implements OnClickListener {
	final String TAG = "HELLOINTENT2/Main";
	public static final int REQ_SEC = 1;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "onStart()");
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
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG,"onPause()");
//		Toast.makeText(getApplicationContext(), "Main/onPause", Toast.LENGTH_SHORT).show();
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
		Log.d(TAG, "onDestory()");
	}



	/** Called when the activity is first created. */
	EditText editSearch;
	Button buttonSearch;
	Button buttonCall;
	Button buttonMap;
	Button buttonSecond;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.main);
        
        editSearch = (EditText)findViewById(R.id.editSearch);
        
        buttonSearch = (Button)findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);
        
        Button buttonWeb = (Button)findViewById(R.id.buttonWeb);
		buttonWeb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://www.google.com");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
        
        buttonCall = (Button)findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(this);
        
        buttonMap = (Button)findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(this);
        
        buttonSecond = (Button)findViewById(R.id.buttonSecond);
//        buttonSecond.setOnClickListener(this);
        buttonSecond.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(getBaseContext(), SecondActivity.class);
        		intent.putExtra("PARAMS_SEC_VALUE", "From Main Activity");
//        		startActivity(intent);
        		startActivityForResult(intent, REQ_SEC);
        	}
        });
    }
    
    @Override
    public void onClick(View v) {
    	if (v == buttonSearch){
    		String strSearch = editSearch.getText().toString();
    		Intent intent = new Intent();
    		
    		intent.setAction(Intent.ACTION_WEB_SEARCH);
    		
    		intent.putExtra(SearchManager.QUERY, strSearch);
    		startActivity(intent);
    	}
    	else if (v == buttonCall) {
    		Uri uri = Uri.parse("tel:0101231234");
    		
    		Intent intent = new Intent(Intent.ACTION_DIAL, uri);
    		startActivity(intent);
    	}
    	else if (v == buttonMap) {
    		Uri uri = Uri.parse("geo:38.899533,-77.036476");
    		
    		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    		startActivity(intent);
    	}
//    	else if (v == buttonSecond) {   		
//    		Intent intent = new Intent(this, SecondActivity.class);
//    		startActivity(intent);
//    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d(TAG, "onActivityResult(): reqCode " + requestCode
    			+ "  resCode " + resultCode 
    			+ "(" + Activity.RESULT_OK + ")");
    	if (requestCode == REQ_SEC) {
    		if (resultCode == Activity.RESULT_OK) {
    			String val = data.getStringExtra("RESULT_SEC_VALUE");
    			Toast.makeText(getApplicationContext(), val, 
    					Toast.LENGTH_LONG).show();
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
}