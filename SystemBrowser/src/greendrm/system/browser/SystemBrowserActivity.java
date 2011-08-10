/* 
 * Copyright 2011 Dojip Kim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package greendrm.system.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SystemBrowserActivity extends ListActivity {
	private static final boolean DEBUG = true;
	private static final String TAG = "SystemBrowser";
	
	private enum DISPLAYMODE { ABSOLUTE, RELATIVE; }
	
	private final DISPLAYMODE displayMode = DISPLAYMODE.RELATIVE;
	private List<IconifiedText> directoryEntries = new ArrayList<IconifiedText>();
	private File currentDirectory = new File("/");
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        browseToRoot();
        
        ListView lv = getListView();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int pos, long id) {
				onListItemLongClick(getListView(), v, pos, id);
				return false;
			}
		
        });
    }
    
	@Override
	public void onBackPressed() {
		if (this.currentDirectory.getParent() == null)
			super.onBackPressed();
		else
			upOneLevel();
	}
	
    /**
     * This function browses to the root directory of the filesystem.
     */
    private void browseToRoot() {
    	browseTo(new File("/"));
    }
    
    /**
     * This function browses up one level according to the filed: currentDirectory
     */
    private void upOneLevel() {
    	if (this.currentDirectory.getParent() != null)
    		this.browseTo(this.currentDirectory.getParentFile());
    }
    
    private void browseTo(final File aDirectory) {
    	// On relative we display the full path in the title.
    	if (aDirectory.canRead() == false) {
			Toast.makeText(this, "Can not access", Toast.LENGTH_SHORT).show();
			return;
		}
    	
    	if (this.displayMode == DISPLAYMODE.RELATIVE)
    		this.setTitle(aDirectory.getAbsolutePath());
    	if (aDirectory.isDirectory()) {
    		this.currentDirectory = aDirectory;
    		fill(aDirectory.listFiles());
    	}
    	else {
    		DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {

    			@Override
    			public void onClick(DialogInterface diaglog, int which) {
    				// Lets start an intent to View the file, that was clicked..
    				SystemBrowserActivity.this.openFile(aDirectory);
    			}
    		};
    		DialogInterface.OnClickListener cancelButtonListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SystemBrowserActivity.this.setTitle(SystemBrowserActivity.this.currentDirectory.getAbsolutePath());
					dialog.dismiss();
				}
    		};
    		new AlertDialog.Builder(this)
    			.setTitle("Open?")
    			.setNeutralButton("OK", okButtonListener)
    			.setNegativeButton("Cancel", cancelButtonListener)
    			.setIcon(R.drawable.folder)
    			.show();
    	}
    }
    
    private void openFile(File aFile) {
    	MimeTypeMap mime = MimeTypeMap.getSingleton();
    	String type = mime.getMimeTypeFromExtension(getFileEndings(aFile.getName()));
    	
    	if (DEBUG) Log.d(TAG, aFile.getAbsolutePath() + " type: " + type);
    	if (type == null) {
    		if (DEBUG) Log.d(TAG, "set type as 'text/plain'");
    		type = "text/plain";
    	}
    	
    	Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
    	intent.setDataAndType(Uri.parse("file://" + aFile.getAbsolutePath()), type);
    	try {
    		startActivity(intent);
    	}
    	catch (ActivityNotFoundException e) {
    		Toast.makeText(this, "No application for this file", Toast.LENGTH_LONG).show();
    	}
    }
    
    private void fill(File[] files) {
    	this.directoryEntries.clear();
    	
    	// Add the "." == "current directory"
    	this.directoryEntries.add(new IconifiedText(
    			getString(R.string.current_dir),
    			getResources().getDrawable(R.drawable.folder)));
    	
    	// and the ".." == "Up one level"
    	if (this.currentDirectory.getParent() != null)
    		this.directoryEntries.add(new IconifiedText(
    			getString(R.string.up_one_level),
    			getResources().getDrawable(R.drawable.uponelevel)));
    	
    	Drawable currentIcon = null;
    	for (File currentFile : files) {
    		if (currentFile.isDirectory()) {
    			currentIcon = getResources().getDrawable(R.drawable.folder);
    		}
    		else {
    			String fileName = currentFile.getName();
    			/* Determine the Icon to be used,
    			 * depending on the FileEndings defined in:
    			 * res/values/fileendings.xml.
    			 */
    			if (checkEndsWithInStringArray(fileName,
    					getResources().getStringArray(R.array.fileEndingImage))) {
    				currentIcon = getResources().getDrawable(R.drawable.image);
    			}
    			else if (checkEndsWithInStringArray(fileName,
    					getResources().getStringArray(R.array.fileEndingWebText))) {
    				currentIcon = getResources().getDrawable(R.drawable.webtext);
    			}
    			else if (checkEndsWithInStringArray(fileName,
    					getResources().getStringArray(R.array.fileEndingPackage))) {
    				currentIcon = getResources().getDrawable(R.drawable.packed);
    			}
    			else if (checkEndsWithInStringArray(fileName,
    					getResources().getStringArray(R.array.fileEndingAudio))) {
    				currentIcon = getResources().getDrawable(R.drawable.audio);
    			}
    			else {
    				currentIcon = getResources().getDrawable(R.drawable.text);
    			}
    		}
    		switch (this.displayMode) {
    		case ABSOLUTE:
    			/* On absolute Mode, we show the full path */
    			this.directoryEntries.add(new IconifiedText(currentFile.getPath(),
    					currentIcon));
    			break;
    		case RELATIVE:
    			/* On relative Mode, we have to cut the current path at the beginning */
    			int currentPathStringLength = this.currentDirectory.getAbsolutePath().length();
    			this.directoryEntries.add(new IconifiedText(
    					currentFile.getAbsolutePath().substring(currentPathStringLength),
    					currentIcon));
    			break;
    		}
    	}
    	Collections.sort(this.directoryEntries);
    	
    	IconifiedTextListAdapter itla = new IconifiedTextListAdapter(this);
    	itla.setListItems(this.directoryEntries);
    	this.setListAdapter(itla);
    }
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String selectedFileString = this.directoryEntries.get(position)
				.getText();
		if (selectedFileString.equals(getString(R.string.current_dir))) {
			// Refresh
			this.browseTo(this.currentDirectory);
		} else if (selectedFileString.equals(getString(R.string.up_one_level))) {
			this.upOneLevel();
		} else {
			File clickedFile = null;
			switch (this.displayMode) {
				case RELATIVE:
					clickedFile = new File(this.currentDirectory
							.getAbsolutePath()
							+ this.directoryEntries.get(position)
									.getText());
					break;
				case ABSOLUTE:
					clickedFile = new File(this.directoryEntries.get(
							position).getText());
					break;
			}
			if (clickedFile != null)
				this.browseTo(clickedFile);
		}
	}
	
	protected void onListItemLongClick(ListView l, View v, int position, long id) {
		String selectedFileString = this.directoryEntries.get(position)
		.getText();
		
		if (selectedFileString.equals(getString(R.string.current_dir))) {
		} else if (selectedFileString.equals(getString(R.string.up_one_level))) {
		} else {
			File clickedFile = null;
			switch (this.displayMode) {
				case RELATIVE:
					clickedFile = new File(this.currentDirectory
							.getAbsolutePath()
							+ this.directoryEntries.get(position)
									.getText());
					break;
				case ABSOLUTE:
					clickedFile = new File(this.directoryEntries.get(
							position).getText());
					break;
			}
			if (clickedFile != null) {
				Intent intent = new Intent(getBaseContext(), OptionsActivity.class);
				if (DEBUG) Log.d(TAG, "clickedFile: " + clickedFile);
				intent.putExtra(OptionsActivity.PARAMS_FILENAME, clickedFile.getAbsolutePath());
				startActivity(intent);
			}
		}
	}
     /** Checks whether checkItsEnd ends with
      * one of the Strings from fileEndings */
     private boolean checkEndsWithInStringArray(String checkItsEnd,
                         String[] fileEndings){
          for(String aEnd : fileEndings){
               if(checkItsEnd.endsWith(aEnd))
                    return true;
          }
          return false;
     }
     
     private String getFileEndings(String fileName) {
    	 String[] tmps;
    	 String ending;
    	 
    	 tmps = fileName.split("\\.");
    	 if (tmps.length > 1)
    		 ending = tmps[tmps.length - 1];
    	 else
    		 ending = null;
    	 
    	 if (DEBUG) Log.d(TAG, "getFileEndings: " + ending);
    	 return ending;
     }
}