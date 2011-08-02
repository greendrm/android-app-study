package greendrm.thread.hw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThreadHWActivity extends Activity {
	private TextView txtSum, txtCount;
	private Button btSum, btCount;
	private Handler mHandler = new Handler();
	private int mCount = 0;
	private int mSum = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtSum = (TextView)findViewById(R.id.textViewSum);
        txtCount = (TextView)findViewById(R.id.textViewCount);
        
        btSum = (Button)findViewById(R.id.button1);
        btCount = (Button)findViewById(R.id.button2);
        
        txtSum.setText(""+0);
        txtCount.setText(""+0);
        
        btSum.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i <= 300; i++) {
							mSum += i;
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									txtSum.setText(""+mSum);
								}
							});

							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});
        
        btCount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtCount.setText(""+mCount++);
				
			}
		});
    }
}