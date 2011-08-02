package com.pointchips.i2ctest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class I2CTestActivity extends Activity {
	private static final boolean DEBUG = true;
	private static final String TAG = "I2CTest";
	
	private EditText editReg, editVal;
	//private Button buttonRead, buttonWrite;
	private EditText editConsole;
	
	int mReg = 0, mVal = 0;
	String mConsole;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mConsole = new String();
        
        editReg = (EditText)findViewById(R.id.editTextReg);
        editVal = (EditText)findViewById(R.id.editTextVal);
        editConsole = (EditText)findViewById(R.id.editTextConsole);
        
        editReg.setText(""+mReg);
        editVal.setText(""+mVal);
    }
    
    public void onClickRead(View v) {
    	getValue();
    	
    	editConsole.append("clicked Read " + StringEx.toHexString(mReg) + ", " + StringEx.toHexString(mVal) + "\n");
    }
    
    public void onClickWrite(View v) {
    	getValue();
    	
    	editConsole.append("clicked Write " + StringEx.toHexString(mReg) + ", " + StringEx.toHexString(mVal) + "\n");
    }
    
    public boolean getValue() {
    	StringEx reg = new StringEx(editReg.getText().toString());
    	StringEx val = new StringEx(editVal.getText().toString());
    	
    	
    	if (reg.length() == 0) {
    		Toast.makeText(this, "You should set the reg (and val)", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	if (reg.isNumber()) {
    		if (reg.isHex())
    			mReg = reg.parseHex();
    		else
    			mReg = reg.parseInt();
    			
    	}
    	
    	if (val.isNumber()) {
    		if (val.isHex())
    			mVal = val.parseHex();
    		else
    			mVal = val.parseInt();
    			
    	}
    	
    	return true;
    }
}