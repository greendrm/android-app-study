package com.greendrm.hellointent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AndroidHelloIntentActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnIntent = (Button)findViewById(R.id.button1);
        btnIntent.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Uri myPerson = 
        			Uri.parse("content://contacts/people");
        		Intent tmpIntent = 
        			new Intent(Intent.ACTION_VIEW, myPerson);
        			startActivity(tmpIntent);
        	}
        });
    }
}