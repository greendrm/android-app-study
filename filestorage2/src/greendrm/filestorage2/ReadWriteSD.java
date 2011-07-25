package greendrm.filestorage2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

public class ReadWriteSD extends Activity {
	private final static String TAG = "readwriteSD";
	TextView readOutput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readwritesd);
		
		this.readOutput = (TextView) findViewById(R.id.textView8);
		
		String fileName = "testfile-" + System.currentTimeMillis() + ".txt";
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File dir = Environment.getExternalStorageDirectory();
			File file = new File(dir, fileName);
			try {
				file.createNewFile();
			} catch (IOException e) {
				Log.e(TAG, "error creating file", e);
			}
			
			if (file.exists() && file.canWrite()) {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file);
					fos.write("I fear you speak upon the rack, where men enforced do speak anything.".getBytes());
				} catch (IOException e) {
					//
				} finally {
					if (fos != null) {
						try {
							fos.flush(); fos.close();
						} catch (IOException e) {
						}
					}
				}
			} else {
			}
			
			if (file.exists() && file.canRead()) {
				try {
					String str;
					FileReader fr = new FileReader(file);
		    		BufferedReader br = new BufferedReader(fr);
		    		
		    		while ((str = br.readLine()) != null)
		    			readOutput.append(str);
		    		br.close();
				}
				catch (IOException e) {
					//
				}
			}
			
		} else {
			Log.e(TAG, "No SD card!");
		}
	}

}
