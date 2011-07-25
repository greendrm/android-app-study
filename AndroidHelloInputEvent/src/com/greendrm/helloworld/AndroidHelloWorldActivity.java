package com.greendrm.helloworld;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class AndroidHelloWorldActivity extends Activity {
	private HelloView view;
	private Random color;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new HelloView(this);
        setContentView(view);
       	color = new Random();
    }
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
    	
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
    	switch (event.getAction()) {
    	case MotionEvent.ACTION_DOWN:
    		view.setColor(color.nextInt());
    		view.setPos(event.getX(), event.getY());
    		break;
    		
    	case MotionEvent.ACTION_MOVE:
    		break;
    		
    	case MotionEvent.ACTION_UP:
    		view.invalidate();
    		break;
    	}
    	return true;
	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//return super.onKeyDown(keyCode, event);
		if (event.getAction() == KeyEvent.ACTION_DOWN){
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				view.m_x -= 5;
				view.invalidate();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				view.m_x += 5;
				view.invalidate();
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				view.m_y -= 5;
				view.invalidate();
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				view.m_y += 5;
				view.invalidate();
				break;
			case KeyEvent.KEYCODE_DPAD_CENTER:
				//view.invalidate();
				break;
			}
		}
		
		return false;
	}
    
}
