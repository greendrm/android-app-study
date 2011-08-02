package android.activity.thread.oops;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HandlerActivity extends Activity {
	ProgressBar bar;
	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			bar.incrementProgressBy(5);
		}
	};
	boolean isRunning=true;
    private TextView text;
    private Button button;
    private int mcount = 0;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		bar=(ProgressBar)findViewById(R.id.progress);
		bar.setProgress(0);
		text = (TextView)findViewById(R.id.text);
		button = (Button)findViewById(R.id.button);
		button.setOnClickListener( new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mcount ++;
                text.setText( "클릭수: " + mcount);
            }
        });
	}
	
	public void onStart() {
		super.onStart();
		for(int i=0;i<20 && isRunning;i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bar.incrementProgressBy(5);
        }
		isRunning = false;
	}
	
	public void onStop() {
		super.onStop();
		isRunning=false;
	}
}