package greendrm.editor;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class EditorFileSD extends EditorFile {
	private final static String TAG = "EditorFileSd";
	public EditorFileSD() {
		super();
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File dir = Environment.getExternalStorageDirectory();
			baseDir = dir;
		}
		else {
			Log.e(TAG, "No SD Card!");
		}
	}
		
}
