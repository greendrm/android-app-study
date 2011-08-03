package greendrm.http.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HttpReaderActivity extends Activity {
	private static final String TAG = "HttpReader";
	private Button btGo;
	private EditText etUrl;
	private EditText etView;
	private ProgressDialog mProgress;
	private Handler mHandler = new Handler();
	
	private String mUrl;
	private StringBuilder mHtml;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btGo = (Button)findViewById(R.id.buttonGo);
        etUrl = (EditText)findViewById(R.id.editTextURL);
        etView = (EditText)findViewById(R.id.editText1);

        btGo.setOnClickListener(new View.OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		
        		new Thread (new Runnable() {

        			@Override
        			public void run() {
        				mHandler.post(new Runnable() {
        					@Override
        					public void run() {
        						mProgress = ProgressDialog.show(HttpReaderActivity.this, "지금...", "다운로딩...");
        					}
        				});
        				mHtml = getHttp();
        				Log.d(TAG, "Complete get http");
        				mHandler.post(new Runnable() {
        					@Override
        					public void run() {
        						mProgress.dismiss();
        						etView.setText(mHtml.toString());
        					}
        				});

        			}
        		}).start();
        	}
        });
    }
    
    private StringBuilder getHttp() {
    	mUrl = etUrl.getText().toString();
		Log.d(TAG, mUrl);
		StringBuilder html = new StringBuilder();
		try {
			URL url = new URL(mUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(1000);
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					for(;;) {
						String line = br.readLine();
						if (line == null) break;
						html.append(line + '\n');
					}
					br.close();
				}
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			Log.e(TAG, "Malformed URL");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "IOException");
			e.printStackTrace();
		}
		
		return html;
    }
}