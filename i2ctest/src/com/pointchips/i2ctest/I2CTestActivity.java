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
    	
    	editConsole.append("clicked Read " + toHexString(mReg) + ", " + toHexString(mVal) + "\n");
    }
    
    public void onClickWrite(View v) {
    	getValue();
    	
    	editConsole.append("clicked Write " + toHexString(mReg) + ", " + toHexString(mVal) + "\n");
    }
    
    public boolean getValue() {
    	String reg = editReg.getText().toString();
    	String val = editVal.getText().toString();
    	
    	if (reg.length() == 0) {
    		Toast.makeText(this, "You should set the reg (and val)", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	if (isNumber(reg)) {
    		if (isHex(reg))
    			mReg = parseHex(reg);
    		else
    			mReg = Integer.parseInt(reg);
    			
    	}
    	
    	if (isNumber(val)) {
    		if (isHex(val))
    			mVal = parseHex(val);
    		else
    			mVal = Integer.parseInt(val);
    			
    	}
    	
    	return true;
    }
    
    private boolean isNumber(String str) {
    	boolean hex = false;
    	
    	if (str == null || str.length() == 0)
    		return false;
    	
    	for (int i = 0; i < str.length(); i++) {
    		char c = str.charAt(i);
    		
    		if (c >= '0' && c <= '9')
    			continue;
    		hex = true;
    		if (c >= 'a' && c <= 'f')
    			continue;
    		if (c >= 'A' && c <= 'F')
    			continue;
    		if (c == 'x')
    			continue;
    		
    		return false;
    	}
    	
    	if (hex) {
    		if (isHex(str) == false)
    			return false;
    	}
    	return true;
    }
    
    private boolean isHex(String str) {
    	if (str == null || str.length() == 0)
    		return false;
    	
    	for (int i = 0; i < str.length(); i++) {
    		if (str.charAt(i) == '0') {
    			if (str.length() > i+1) {
    				if (str.charAt(i+1) == 'x') {
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    private boolean isDec(String str) {
    	if (str == null || str.length() == 0)
    		return false;
    	
    	if (isNumber(str))
    		if (isHex(str) == false)
    			return true;
    	return false;
    }
    
    private int parseHex(String str) {
    	int res = 0;
    	
    	String[] tmp;
    	tmp = str.split("0x");
    	if (tmp.length < 2)
    		return 0;
    	
    	str = tmp[1];
    	
    	if (str.length() == 0)
    		return 0;
    	
    	int j = 0;
    	for (int i = str.length()-1; i >= 0; i--) {
    		int d = 0;
    		char c = str.charAt(i);
    		if (c >= '0' && c <= '9')
    			d = c - '0';
    		else if (c >= 'A' && c <= 'F')
    			d = c - 'A' + 10;
    		else if (c >= 'a' && c <= 'f')
    			d = c - 'a' + 10;
    		else
    			return 0;
    		res += d * (1 << (j++*4));
    		
    	}
    	return res;
    }
    
    private String toHexString(int d) {
    	String str = new String();

    	do {
    		int r = d % 16;
    		if (r < 10)
    			str = ""+r + str;
    		else
    			str = (char)('a'+ r - 10) + str;

    	} while ((d /= 16) > 0);
    	str = "0x" + str;
    	
    	return str;
    }
    
}