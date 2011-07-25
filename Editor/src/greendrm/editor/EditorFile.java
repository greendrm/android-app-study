package greendrm.editor;

import java.io.File;
import java.io.IOException;

import android.util.Log;

public class EditorFile {
	private final static String TAG = "EditorFile";
	protected File baseDir = null;
	
	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}
	
	public File getBaseDir() {
		return baseDir;
	}
	
	public void mkDir(String filename) {
		File file = new File(baseDir, filename);
		if (file.mkdir() || file.exists()) {
			this.baseDir = file;
		}
		else {
			Log.e(TAG, "Can not make directory(" + filename + ")");
		}
	}
	
	public void createFile(String filename)	{
		File file = new File(baseDir, filename);
		try {
			file.createNewFile();
			Log.d(TAG, "createFile( " + file.getName() + " )");
		} catch (IOException e) {
			Log.e(TAG, "Can not create file", e);
		}
	}
}
