package greendrm.editor;


import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EditorActivity extends Activity {
	private final static String TAG = "Editor";
	ListView list;
	ArrayList<String> files;
	ArrayAdapter<String> adapter;
	String mDirName = "김도집";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditorFileSD fileSD = new EditorFileSD();
        //File file;
        
        list = (ListView)findViewById(R.id.listView1);
        files = new ArrayList<String>();
        
        fileSD.mkDir(mDirName);
        fileSD.createFile("A.txt");
        fileSD.createFile("B.txt");
        fileSD.createFile("C.txt");
        
        File baseDir = fileSD.getBaseDir();
        File[] fs = baseDir.listFiles();
        
        for (int i = fs.length - 1; i >= 0; i--) {
        	files.add(fs[i].getPath());
        }
        
        
        adapter = new ArrayAdapter<String>(this, R.layout.listrow, files);
        list.setAdapter(adapter);
    }
    
    public void onClickAddFile(View v) {
		Intent intent = new Intent(getBaseContext(), EditorContents.class);
		intent.putExtra("PARAMS_SEC_VALUE", "From Main Activity");
		startActivityForResult(intent, 0);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d(TAG, "onActivityResult(): reqCode " + requestCode
    			+ "  resCode " + resultCode 
    			+ "(" + Activity.RESULT_OK + ")");
    	if (requestCode == 0) {
    		if (resultCode == Activity.RESULT_OK) {
    			String val = data.getStringExtra("RESULT_SEC_VALUE");
    			Toast.makeText(getApplicationContext(), val, 
    					Toast.LENGTH_LONG).show();
    			
    			EditorFileSD fileSD = new EditorFileSD();
    			fileSD.mkDir("김도집");
    		    File baseDir = fileSD.getBaseDir();
    	        File[] fs = baseDir.listFiles();
    	        
    	        files.clear();
    	        for (int i = fs.length - 1; i >= 0; i--) {
    	        	files.add(fs[i].getPath());
    	        }
    	        
    	        adapter = new ArrayAdapter<String>(this, R.layout.listrow, files);
    	        list.setAdapter(adapter);
    			
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
}