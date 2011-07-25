package greendrm.filestorage2;

import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ReadFile extends Activity {
	private final static String TAG = "readfile";
	TextView readOutput;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readfile);
		
		readOutput = (TextView)findViewById(R.id.textView2);
		
		FileInputStream fis = null;
		try {
			fis = openFileInput("filename.txt");
			byte[] reader = new byte[fis.available()];
			while (fis.read(reader) != -1) {}
			this.readOutput.setText(new String(reader));
		}
		catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				}
				catch (IOException e) {
					//
				}
			}
		}
	}
    
    public void onClickReadResource(View v) {
    	startActivity(new Intent(ReadFile.this, ReadRawResourceFile.class));
    }
}
