package greendrm.editor;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class EditorActivity extends Activity {
	private static final boolean DEBUG = true;
	private static final String TAG = "Editor";
	private ListView list;
	private ArrayList<String> items;
	private ArrayAdapter<String> adapter;
	
	private String mSaveMethod = "sdcard";
	
	private final String mDirName = "김도집";
	private String mTmpPath = null;
	
	private EditorFileSD mFileSD = null;
	private EditorFileDatabase mDatabase = null;
	
	private TextView eMode1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	if (DEBUG) Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mFileSD = new EditorFileSD(mDirName);
        mDatabase = new EditorFileDatabase(this, "editor_db", 1);
        items = new ArrayList<String>();
        
        eMode1 = (TextView)findViewById(R.id.textViewMode1);
        
        list = (ListView)findViewById(R.id.listView1);
        if (mSaveMethod.equals("db"))
        	items = mDatabase.retreiveFiles(items);
        else
        	items = mFileSD.retreiveFiles(items);
       
        adapter = new ArrayAdapter<String>(this, R.layout.listrow, items);
        list.setAdapter(adapter);
        
        // register the listener of list
        list.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id) {
        		String str;
        		if (mSaveMethod.equals("db"))
        			str = mDatabase.parseFileName(items.get(position).toString());
        		else
        			str = mFileSD.parseFileName(items.get(position).toString());
        		Intent intent = new Intent(getBaseContext(), EditorContents.class);
        		intent.putExtra("FILE_NAME", str);
        		startActivityForResult(intent, 0);
        	}
        });
        
        list.setOnItemLongClickListener(new OnItemLongClickListener() {
        	
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mTmpPath = items.get(position);
				
				DialogInterface.OnClickListener deleteListener = 
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (mSaveMethod.equals("db")) {
							mDatabase.deleteFile(mFileSD.parseFileName(mTmpPath));
							items = mDatabase.retreiveFiles(items);
						}
						else {
							mFileSD.deleteFile(mFileSD.parseFileName(mTmpPath));
							items = mFileSD.retreiveFiles(items);
						}
						adapter.notifyDataSetChanged();
					}
				};
				
				DialogInterface.OnClickListener cancelListener = 
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						mTmpPath = null;
					}
				};
				
				new AlertDialog.Builder(EditorActivity.this)
					.setTitle("Delete?")
					.setNeutralButton("Delete", deleteListener)
					.setNegativeButton("Cancle", cancelListener)
					.show();
				
				return false;
			}
        });
    }
	
	@Override
	protected void onDestroy() {
		mFileSD.setBaseDir(null);
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem itemSetting = menu.add(0, 0, Menu.NONE, "Preferences");
		MenuItem itemRem = menu.add(0, 1, Menu.NONE, "Exit");
		itemSetting.setIcon(android.R.drawable.ic_menu_preferences);
		itemRem.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			startActivity(new Intent(this, PreferencesActivity.class));
			break;
		case 1:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void onClickAddFile(View v) {
		Intent intent = new Intent(getBaseContext(), EditorContents.class);
		startActivityForResult(intent, 0);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.d(TAG, "onActivityResult(): reqCode " + requestCode
    			+ "  resCode " + resultCode 
    			+ "(" + Activity.RESULT_OK + ")");
    	if (requestCode == 0) {
    		if (resultCode == Activity.RESULT_OK) {
    			if (mSaveMethod.equals("db"))
    				items = mFileSD.retreiveFiles(items);
    			else
    				items = mFileSD.retreiveFiles(items);
    			adapter.notifyDataSetChanged();
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    protected void onResume() {
    	if (DEBUG) Log.d(TAG, "onResume");
        super.onResume();
        
        retrivePreferences();

        if (mSaveMethod.equals("db")) {
        	items = mDatabase.retreiveFiles(items);
        }
        else if (mSaveMethod.equals("sdcard")) {
        	items = mFileSD.retreiveFiles(items);
        }
        else {
        	mSaveMethod = "sdcard";
        	Log.w(TAG, mSaveMethod + " : Not yet supported");
        	//Toast.makeText(this, "Not yet supported.\n", Toast.LENGTH_LONG);
        	items = mFileSD.retreiveFiles(items);
        }
        adapter.notifyDataSetChanged();
        
        if (DEBUG) Log.d(TAG, "onResume: " + mSaveMethod );
        eMode1.setText(mSaveMethod);
    }
    
    private void retrivePreferences() {
        SharedPreferences prefs = PreferenceManager.
            getDefaultSharedPreferences(getApplicationContext());
        
        mSaveMethod = prefs.getString("saveMethod", "sdcard");
        if (DEBUG) Log.d(TAG, "save method: " + mSaveMethod);
    }

}