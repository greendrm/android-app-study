package greendrm.helloservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AndroidHelloServiceActivity extends Activity implements OnClickListener {
	private Intent intentService;
	private TestService.TestBinder binder;
	private TestServiceConnection serviceConnection;
	
	Button buttonService;
	Button buttonBinder;
	Button buttonIncrement;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonService = (Button)findViewById(R.id.buttonService);
        buttonBinder = (Button)findViewById(R.id.buttonBinder);
        buttonIncrement = (Button)findViewById(R.id.buttonIncrement);
        
        buttonService.setOnClickListener(this);
        buttonBinder.setOnClickListener(this);
        buttonIncrement.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View v) {
		if (v == buttonService) {
			if (intentService == null) {
				intentService = new Intent(this, TestService.class);
				ComponentName serviceName = startService(intentService);
				if (serviceName != null) {
					buttonService.setText(getResources().getText(R.string.servicestop));
				}
				else {
					intentService = null;
				}
			}
			else {
				if (stopService(intentService)) {
					intentService = null;
					buttonService.setText(getResources().getText(R.string.servicestart));
					
				}
			}
		}
		else if (v == buttonBinder) {
			if (binder == null) {
				serviceConnection = new TestServiceConnection();
				Intent intent = new Intent(this, TestService.class);
				if (bindService(intent, serviceConnection,
						Context.BIND_AUTO_CREATE)) {
					buttonBinder.setText(getResources().getText(R.string.binderUnbind));
				}
				else {
					binder = null;
				}
			}
			else {
				unbindService(serviceConnection);
				binder = null;
				buttonBinder.setText(getResources().getText(R.string.binderService));
			}
		}
		else if (v == buttonIncrement) {
			if (binder != null) {
				binder.increment();
			}
		}
	}
	
    private class TestServiceConnection implements ServiceConnection {
    	@Override
    	public void onServiceConnected(ComponentName name, IBinder _binder) {
    		binder = (TestService.TestBinder)_binder;
    	}
    	
    	@Override
    	public void onServiceDisconnected(ComponentName name) {
    		binder = null;
    	}
    }
}