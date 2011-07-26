package greendrm.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class EditorFile {
	private final static boolean DEBUG = true;
	private final static String TAG = "EditorFile";
	protected static File baseDir = null;
	
	public void setBaseDir(File dir) {
		baseDir = dir;
	}
	
	public File getBaseDir() {
		return baseDir;
	}
	
	public void mkDir(String filename) {
		if (filename == null)
			return;
		
		
		File file = new File(baseDir, filename);
		
		if (DEBUG) Log.d(TAG, "mkDir ( " + file.getPath() + " )");
		
		if (file.exists() || file.mkdir()) {
			baseDir = file;
		}
		else {
			Log.e(TAG, "Can not make directory(" + filename + ")");
		}
	}
	
	public void createFile(String filename)	{
		File file = new File(baseDir, filename);
		try {
			file.createNewFile();
			if (DEBUG) Log.d(TAG, "createFile( " + file.getName() + " )");
		} 
		catch (IOException e) {
			Log.e(TAG, "Can not create file", e);
		}
	}
	
	public void deleteFile(String filename) {
		if (filename == null)
			return;
		
		File file = new File(baseDir, filename);
		if (DEBUG) Log.d(TAG, "deleteFile( " + file.getPath() + " )");
		
		if (file.delete() == false) {
			if (DEBUG) Log.d(TAG, "failed to delete file");
		}
	}
	
	public void saveFile(String filename, String data) {
		if (filename == null)
			return;
		
		File file = new File(baseDir, filename);
		
		if (DEBUG) Log.d(TAG, "saveFile : " + file.getPath());
		
		if (file.exists() && file.canWrite()) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(data.getBytes());
			} catch (IOException e) {
				Log.e(TAG, "saveFile error");
			} finally {
				if (fos != null) {
					try {
						fos.flush(); fos.close();
					} catch (IOException e) {
					}
				}
			}
		}
		else {
			Log.d(TAG, "saveFile file is not exits or writable");
		}
	}
	
	public String loadFile(String filename) {
		if (filename == null)
			return null;
		
		File file = new File(baseDir, filename);
		byte[] reader = null;
		FileInputStream fis = null;
		
		if (DEBUG) Log.d(TAG, "loadFile : " + file.getPath());
		
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);

				reader = new byte[fis.available()];
				if(DEBUG) Log.d(TAG, "fis.available " + fis.available());
				while (fis.read(reader) != -1 && fis.available() > 0) {}
			} catch (IOException e) {
				Log.e(TAG, "loadFile error");
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
			}
		}
		else {
			Log.w(TAG, "loadFile file is not existed");
		}
		if (reader != null) {
			return new String(reader);
		}
		else {
			if (DEBUG) Log.d(TAG, "loadFile returned null");
			return null;
		}
	}
	
    public String parseFileName(String fullPath) {
		String[] tmps = fullPath.split("\\/"); 
		return tmps[tmps.length-1];
    }
    
    public String parseFileNameNoExt(String filename) {
    	if (filename != null) {
    		String[] tmps = filename.split("\\."); 
    		return tmps[0];
    	}
    	return null;
    }
}
