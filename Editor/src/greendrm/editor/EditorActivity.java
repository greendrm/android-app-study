package greendrm.editor;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EditorActivity extends Activity {
	private final static String TAG = "editor";
	ListView list;
	ArrayList<String> files;
	ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.listView1);
        files = new ArrayList<String>();
        
        files.add("a.txt");
        files.add("b.txt");
        
        adapter = new ArrayAdapter<String>(this, R.layout.listrow, files);
        list.setAdapter(adapter);
    }
    
    public void createSDDir(String dirName) {
    	String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File dir = Environment.getExternalStorageDirectory();
			File file = new File(dir, dirName);
			file.mkdir();
		}
		else {
			//
		}
    }
    
    public void createSDFile(String fileName) {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File dir = Environment.getExternalStorageDirectory();
			File file = new File(dir, fileName);
			try {
				file.createNewFile();
			} catch (IOException e) {
				Log.e(TAG, "error creating file", e);
			}
		}
		else {
			//
		}
    }
}