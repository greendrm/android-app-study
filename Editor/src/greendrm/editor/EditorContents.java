package greendrm.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditorContents extends Activity {
	private final static String TAG = "EditorContents";
	EditText editFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		
		editFile = (EditText)findViewById(R.id.editNewFile);
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause()");
		EditorFileSD fileSD = new EditorFileSD();
		fileSD.mkDir("김도집");
		fileSD.createFile(editFile.getText().toString());
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//return super.onKeyDown(keyCode, event);
		if (event.getAction() == KeyEvent.ACTION_DOWN){
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Log.d(TAG, "KEYCODE_BACK");
	    		Intent data = new Intent();
	    		data.putExtra("RESULT_SEC_VALUE", "From Second Activity");
	    		setResult(Activity.RESULT_OK, data);
	    		finish();
				break;
			}
		}
		return false;
	}

}
