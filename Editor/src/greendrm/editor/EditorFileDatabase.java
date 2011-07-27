package greendrm.editor;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EditorFileDatabase extends SQLiteOpenHelper {
	private final static boolean DEBUG = true;
	private final static String TAG = "EditorFileDatabase";
	
	static Context CONTEXT = null;
	static String DATABASE_NAME = "editor.db";
	private static String DATABASE_TABLE = "test_db";
	static int DATABASE_VERSION = 1;
	
	public EditorFileDatabase() {
		super(CONTEXT, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public EditorFileDatabase(Context context, String database_name,
			int database_version) {
		super(context, database_name, null, database_version);
		CONTEXT = context;
		DATABASE_NAME = database_name;
		DATABASE_VERSION = database_version;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		if (DEBUG) Log.d(TAG, "onCreate");
		db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + "_id INTEGER PRIMARY KEY,"
				+ "filename VARCHAR, " + "data TEXT);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (DEBUG) Log.d(TAG, "onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}
	
	public void setBaseDir(String dir) {
		if (DEBUG) Log.d(TAG, "setBaseDir");
		DATABASE_NAME = dir;
	}
	
	public String getBaseDir() {
		if (DEBUG) Log.d(TAG, "getBaseDir");
		return DATABASE_NAME;
	}
	
	public void mkDir(String filename) {
		if (DEBUG) Log.d(TAG, "mkDir");
		DATABASE_NAME = filename;
		SQLiteDatabase db = getReadableDatabase();
		onCreate(db);
	}
	
	public void createFile(String filename)	{
		//
	}
	
	public void deleteFile(String filename) {
		if (DEBUG) Log.d(TAG, "deleteFile");
		if (filename == null)
			return;
		
		SQLiteDatabase db = getReadableDatabase();
		String deleteMe = "DELETE FROM " + DATABASE_TABLE;
		db.execSQL(deleteMe + " WHERE " + 
				"filename=" + "'" + filename + "'");
	}
	
	public void saveFile(String filename, String data) {
		if (DEBUG) Log.d(TAG, "saveFile");
		if (filename == null)
			return;
		
		SQLiteDatabase db = getWritableDatabase();
		String insertMe = "INSERT INTO " + DATABASE_TABLE
		+ "(filename, data) " + "VALUES ";
		db.execSQL(insertMe + "('" + filename + "'," + "'"
				+ data + "'" + ");");
	}
	
	public String loadFile(String filename) {
		if (DEBUG) Log.d(TAG, "loadFile");
		if (filename == null)
			return null;
		
		String data = null;
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(DATABASE_TABLE, new String[] {"_id", "filename", "data"}, null, null, null, null, null);
		try {
			//int idColumn = c.getColumnIndex("_id");
			int filenameColumn = c.getColumnIndex("filename");
			int dataColumn = c.getColumnIndex("data");
			int i = 0;
			if (c.moveToFirst()) {
				do {
					i++;
					//int id = c.getInt(idColumn);
					String _filename = c.getString(filenameColumn);
					data = c.getString(dataColumn);
					if (_filename.equals(filename))
						break;
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) c.close();
		}
		return data;
	}
	
    public String parseFileName(String fullPath) {
    	if (DEBUG) Log.d(TAG, "parseFileName");
		String[] tmps = fullPath.split("\\/"); 
		return tmps[tmps.length-1];
    }
    
    public String parseFileNameNoExt(String filename) {
    	if (DEBUG) Log.d(TAG, "parseFileNameNoExt");
    	if (filename != null) {
    		String[] tmps = filename.split("\\."); 
    		return tmps[0];
    	}
    	return null;
    }

    public ArrayList<String> retreiveFiles(ArrayList<String> items) {
    	if (DEBUG) Log.d(TAG, "retreiveFiles");
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(DATABASE_TABLE, new String[] {"_id", "filename", "data"}, null, null, null, null, null);
        items.clear();
		try {
			//int idColumn = c.getColumnIndex("_id");
			int filenameColumn = c.getColumnIndex("filename");
			//int dataColumn = c.getColumnIndex("data");
			int i = 0;
			if (c.moveToFirst()) {
				do {
					i++;
					//int id = c.getInt(idColumn);
					String _filename = c.getString(filenameColumn);
					//String data = c.getString(dataColumn);
					items.add(_filename);
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) c.close();
		}
        return items;
    }
}
