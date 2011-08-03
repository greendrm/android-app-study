package greendrm.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WebViewActivity extends Activity {
	private Button btGo;
	private EditText etUrl;
	private WebView wvView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btGo = (Button)findViewById(R.id.button1);
        etUrl = (EditText)findViewById(R.id.editText1);
        wvView = (WebView)findViewById(R.id.webView1);
        
        btGo.setOnClickListener(new View.OnClickListener() {
			private String url;
			
			@Override
			public void onClick(View v) {
				url = etUrl.getText().toString();
				wvView.loadUrl(url);
			}
		});
        
        etUrl.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						keyCode == KeyEvent.KEYCODE_ENTER) {
					String url = etUrl.getText().toString();
					wvView.loadUrl(url);
					return true;
				}
				
				return false;
			}
        	
        });
        
        
        wvView.setWebViewClient(new WebViewClient() {
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		view.loadUrl(url);
        		return true;
        	}
        });
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK
    			&& wvView.canGoBack()) {
    		wvView.goBack();
    		return true;
    	}

		return super.onKeyDown(keyCode, event);
    }
}