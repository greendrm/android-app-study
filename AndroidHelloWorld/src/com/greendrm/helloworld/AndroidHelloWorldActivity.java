package com.greendrm.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AndroidHelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        setContentView(new HelloView(this));
        
        /// 
        Log.d("tag", "안녕하세요....");
    }
}

//protected class HelloView extends View {
//	public HelloView(Context context) {
//		super(context);
//		setBackgroundColor(Color.WHITE);
//	}
//	
//	@Override
//	protected void onDraw(Canvas canvas) {
//		canvas.drawText("Hello World", 0, 25, new Paint());
//	}
//}
