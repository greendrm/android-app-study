package greendrm.filestorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FilestorageActivity extends Activity {
	private static final String FILENAME="test.txt";
	private EditText mText;
	private CheckBox checkExternal;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        checkExternal = (CheckBox)findViewById(R.id.checkBox1);
        mText = (EditText)findViewById(R.id.editText1);
    }
    
    public void onClickSave(View v) throws IOException {
    	if (checkExternal.isChecked())
    		saveToExternal(mText.getText().toString());
    	else
    		saveToInternal(mText.getText().toString());
    }
    
    public void onClickLoad(View v) throws IOException {
    	if (checkExternal.isChecked())
    		mText.setText(loadFromExternal());
    	else
    		mText.setText(loadFromInternal());
    }
    
    private void saveToInternal(String text) throws IOException {
    	FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
    	fos.write(text.getBytes());
    	fos.close();
    }
    
    private CharSequence loadFromInternal() throws IOException {
    	StringBuffer readed = new StringBuffer();
    	String str;
    	FileInputStream fis = openFileInput(FILENAME);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		while ((str = br.readLine()) != null) {
    		readed.append(str);
    	}
    	br.close();
    	return readed.toString();
    }
    
    private void saveToExternal(String text) throws IOException {
    	String state = Environment.getExternalStorageState();
    	if (state.equals(Environment.MEDIA_MOUNTED)) {
    		File dir = Environment.getExternalStorageDirectory();
    		File file = new File(dir, FILENAME);
    		FileOutputStream fos = new FileOutputStream(file);
    		fos.write(text.getBytes());
    		fos.close();
    	}
    	else {
    		Toast.makeText(this, "No SD Card!", Toast.LENGTH_LONG);
    	}
    }
    
    private CharSequence loadFromExternal() throws IOException{
    	String state = Environment.getExternalStorageState();
    	StringBuffer readed= new StringBuffer();
    	if (state.equals(Environment.MEDIA_MOUNTED)) {
    		String str;
    		File dir = Environment.getExternalStorageDirectory();
    		File file= new File(dir, FILENAME);
    		FileReader fr = new FileReader(file);
    		BufferedReader br = new BufferedReader(fr);
    		
    		while ((str = br.readLine()) != null)
    			readed.append(str);
    		br.close();
    	}
    	else {
    		Toast.makeText(this, "No SD Card!", Toast.LENGTH_SHORT);
    	}
    	return readed.toString();
    }
}