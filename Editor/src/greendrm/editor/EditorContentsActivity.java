package greendrm.editor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditorContentsActivity extends Activity {
	private static final boolean DEBUG = true;
	private static final String TAG = "EditorContents";
	private EditText editFile;
	private EditText editContents;
	private boolean isAdd = false;
	
	private IEditorFile mFile = null;
	private EditorFileSD mFileSD = null;
	private EditorFileDatabase mDatabase = null;
	
	private static String mSaveMethod = "sdcard";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		mFileSD = new EditorFileSD();
		mDatabase = new EditorFileDatabase();
		String data;
		
		retrivePreferences();
		
		if (mSaveMethod.equals("db"))
			mFile = mDatabase;
		else
			mFile = mFileSD;
		
		editFile = (EditText)findViewById(R.id.editNewFile);
		editContents = (EditText)findViewById(R.id.editContents);
		TextView eMode2 = (TextView)findViewById(R.id.textViewMode2);
		eMode2.setText(mSaveMethod);
		
		Intent extra = getIntent();
		String filename = extra.getStringExtra("FILE_NAME");
		editFile.setText(mFile.parseFileNameNoExt(filename));
		
		data = mFile.loadFile(filename);
		
		if (data != null)
			editContents.setText(data);
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause()");
		String filename = editFile.getText().toString();
		if (isAdd) {
			String data = editContents.getText().toString();
			if (mSaveMethod.equals("db")) {
				mDatabase.deleteFile(filename);
				mDatabase.saveFile(filename, data);
			}
			else {
				mFileSD.createFile(filename+".txt");
				mFileSD.saveFile(filename+".txt", data);
			}
		}
		else {
			if (mSaveMethod.equals("db"))
				mDatabase.deleteFile(filename);
			else
				mFileSD.deleteFile(filename+".txt");
		}
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();

		
		Intent data = new Intent();
		if (DEBUG) Log.d(TAG, "onBackPressed:" + editFile.getText().toString().length());
		if (editFile.getText().toString().length() != 0) {
			if (DEBUG) Log.d(TAG, "onBackPressed: will be saved");
			isAdd = true;
			setResult(Activity.RESULT_OK, data);
		}
		else {
			if (DEBUG) Log.d(TAG, "onBackPressed: will be discarded");
			isAdd = false;
			setResult(Activity.RESULT_CANCELED, data);
		}
		finish();
	}


	public void onClickDeleteFile(View v) {
		isAdd = false;
		
		Intent data = new Intent();
		setResult(Activity.RESULT_OK, data);
		
		finish();
	}
	
    private void retrivePreferences() {
        SharedPreferences prefs = PreferenceManager.
            getDefaultSharedPreferences(getApplicationContext());
        
        mSaveMethod = prefs.getString("saveMethod", "sdcard");
        if (DEBUG) Log.d(TAG, "save method: " + mSaveMethod);
    }
}
