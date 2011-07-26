package greendrm.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditorContents extends Activity {

	private final static String TAG = "EditorContents";
	private EditText editFile;
	private EditText editContents;
	private boolean isAdd = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		EditorFileSD file = new EditorFileSD();
		
		editFile = (EditText)findViewById(R.id.editNewFile);
		editContents = (EditText)findViewById(R.id.editContents);
		
		Intent extra = getIntent();
		String filename = extra.getStringExtra("FILE_NAME");
		editFile.setText(file.parseFileNameNoExt(filename));
		
		
		String data = file.loadFile(filename);
		if (data != null)
			editContents.setText(data);
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause()");
		EditorFileSD fileSD = new EditorFileSD();
		String filename = editFile.getText().toString() + ".txt";
		if (isAdd) {
			String data = editContents.getText().toString();
			fileSD.createFile(filename);
			fileSD.saveFile(filename, data);
		}
		else {
			fileSD.deleteFile(filename);
			//fileSD.deleteFile(fileSD.parseFileNameNoExt(filename));
		}
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		isAdd = true;
		
		Intent data = new Intent();
		String fileName = editFile.getText().toString() + ".txt";
		data.putExtra("FILE_NAME", fileName);
		data.putExtra("FILE_ACTION", "ADD");
		setResult(Activity.RESULT_OK, data);
		finish();
	}


	public void onClickDeleteFile(View v) {
		isAdd = false;
		
		Intent data = new Intent();
		String fileName = editFile.getText().toString() + ".txt";
		data.putExtra("FILE_NAME", fileName);
		data.putExtra("FILE_ACTION", "DELETE");
		setResult(Activity.RESULT_OK, data);
		
		finish();
	}
}
