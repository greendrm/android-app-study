package greendrm.filestorage2;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateFile extends Activity {
	private final static String TAG = "createfile";
	EditText mCreateEdit;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mCreateEdit = (EditText)findViewById(R.id.editText1);
    }
    
    public void onClickCreateFile(View v) {
    	FileOutputStream fos = null;
    	try {
    		fos = openFileOutput("filename.txt", MODE_PRIVATE);
    		fos.write(mCreateEdit.getText().toString().getBytes());
    	}
    	catch (IOException e) {
    		Log.e(TAG, e.getLocalizedMessage());
    	}
    	finally {
    		if (fos != null) {
    			try {
    				fos.flush();
    				fos.close();
    			}
    			catch (IOException e) {
    				//
    			}
    		}
    	}
    	startActivity(new Intent(CreateFile.this, ReadFile.class));
    }
}