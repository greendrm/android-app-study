package greendrm.helloservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service implements Runnable {
	final String LOG_TAG = "TestService";
	private IBinder binder = new TestBinder();
	private Thread thread;
	private boolean isRun;
	private int count;

	public class TestBinder extends Binder {
		public Integer add(Integer a, Integer b) {
			Log.d(LOG_TAG, "add: " + a + ", " + b + " : " + (a+b));
			return a + b;
		}
		
		public Integer sub(Integer a, Integer b) {
			return a - b;
		}
		
		public Integer mul(Integer a, Integer b) {
			return a * b;
		}
		
		public Double div(Double a, Double b) {
			try {
				return a / b;
			} catch (ArithmeticException e) {
				Log.e(LOG_TAG, "divided by zero");
				return 0.0;
			}
		}
	}
	
	@Override
	public void run() {
		while(isRun) {
			try {
				Log.i(LOG_TAG, "Service is called: " + count);
				Thread.sleep(500);
			} catch (Exception e) {
				//Log.e(LOG_TAG, e.getMessage());
			}
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		isRun = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		isRun = false;
		thread.interrupt();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

}
