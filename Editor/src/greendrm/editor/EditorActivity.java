package greendrm.editor;


import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EditorActivity extends Activity {
	private final static String TAG = "Editor";
	private ListView list;
	private ArrayList<String> files;
	private ArrayAdapter<String> adapter;
	private final String mDirName = "김도집";
	private String mTmpPath = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        EditorFileSD fileSD = new EditorFileSD(mDirName);
        
        list = (ListView)findViewById(R.id.listView1);
        files = new ArrayList<String>();
        
        // register the listener of list
        list.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id) {
        		EditorFileSD file = new EditorFileSD();
        		String str = file.parseFileName(files.get(position).toString());
        		Intent intent = new Intent(getBaseContext(), EditorContents.class);
        		intent.putExtra("FILE_NAME", str);
        		startActivityForResult(intent, 0);
        	}
        });
        
        list.setOnItemLongClickListener(new OnItemLongClickListener() {
        	
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mTmpPath = files.get(position);
				
				DialogInterface.OnClickListener deleteListener = 
					new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditorFileSD file = new EditorFileSD();
						file.deleteFile(file.parseFileName(mTmpPath));
						updateFileList(file.parseFileName(mTmpPath), false);
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

        // update the list of files
	    File baseDir = fileSD.getBaseDir();
        File[] fs = baseDir.listFiles();
        
        files.clear();
        for (int i = fs.length - 1; i >= 0; i--) {
        	files.add(fs[i].getPath());
        }
        
        adapter = new ArrayAdapter<String>(this, R.layout.listrow, files);
        list.setAdapter(adapter);
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		EditorFileSD file = new EditorFileSD();
		file.setBaseDir(null);
		super.onDestroy();
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
    			String fileName = data.getStringExtra("FILE_NAME");
    			String mode = data.getStringExtra("FILE_ACTION");
    			updateFileList(fileName, mode.equals("ADD"));
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    public void updateFileList(String fileName, boolean isAdd) {
		EditorFileSD fileSD = new EditorFileSD();
	    File baseDir = fileSD.getBaseDir();
        File file = new File(baseDir, fileName);
        String fullPath = file.getPath();
        int i = 0;
        
        for (i = 0; i < files.size(); i++) {
        	Log.d(TAG, files.get(i) + ", " + fullPath);
        	if (files.get(i).equals(fullPath)) {
        		Log.d(TAG, "exist matched file");
        		if (isAdd == false)
        			files.remove(i);
        		break;
        	}
        }
    	if (i == files.size() && isAdd)
    		files.add(fullPath);
    	
    	adapter.notifyDataSetChanged();
    }
}