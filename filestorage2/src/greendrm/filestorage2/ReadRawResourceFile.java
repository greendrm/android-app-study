package greendrm.filestorage2;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ReadRawResourceFile extends Activity {
	private final static String TAG = "readrawresource";
	
	TextView readOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readrawresource);
		
		readOutput = (TextView) findViewById(R.id.textView4);
		
		Resources resources = getResources();
		InputStream is = null;
		try {
			is = resources.openRawResource(R.raw.people);
			byte[] reader = new byte[is.available()];
			while (is.read(reader) != -1) {
			}
			this.readOutput.setText(new String(reader));
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// swallow
				}
			}
		}
	}

	public void onClickReadXMLResource(View v) {
		Log.d(TAG, "onClickReadXMLResource");
		startActivity(new Intent(ReadRawResourceFile.this, ReadXMLResourceFile.class));
	}
}
