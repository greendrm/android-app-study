package androidbee.thread.oops.no;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidbee.thread.thread2.R;

public class HandlerActivity extends Activity {
	ProgressBar bar;
	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			bar.incrementProgressBy(5);
		}
	};
	boolean isRunning=false;
    private TextView text;
    private Button button;
    private int mcount = 0;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		bar=(ProgressBar)findViewById(R.id.progress);
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
		bar.setProgress(0);
		
		Thread background=new Thread(new Runnable() {
			public void run() {
				try {
					for (int i=0;i<20 && isRunning;i++) {
						Thread.sleep(1000);
						handler.sendMessage(handler.obtainMessage());
					}
				}
				catch (Throwable t) {
					// just end the background thread
				}
			}
		});
		
		isRunning=true;
		background.start();
	}
	
	public void onStop() {
		super.onStop();
		isRunning=false;
	}
}