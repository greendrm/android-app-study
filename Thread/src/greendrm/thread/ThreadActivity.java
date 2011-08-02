package greendrm.thread;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadActivity extends Activity {
	private ProgressBar mProgress;
	private Button mButton;
	private TextView mText;
	private int count = 0;
	private int nProgress = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mProgress = (ProgressBar)findViewById(R.id.progressBar1);
        mButton = (Button)findViewById(R.id.button1);
        mText = (TextView)findViewById(R.id.textView2);
        
        mProgress.setMax(100);
        
        mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						for (nProgress = 0; nProgress <= 100; nProgress+=10) {
							runOnUiThread(new Runnable() {
								public void run() {
									mProgress.setProgress(nProgress);
								}
							});
							try {
								Thread.sleep(600);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
				
				mText.setText(""+count++);
				
			}
		});
    }
}