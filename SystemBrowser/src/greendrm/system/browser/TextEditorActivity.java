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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TextEditorActivity extends Activity implements OnClickListener {
	private static final boolean DEBUG = true;
	private static final String TAG = "SystemBrowserEditor";

	private File mFile;
	private EditText mText;
	private Button mButtonEdit;
	private Button mButtonExit;
	
	private boolean bEditable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_editor);
		
		mText = (EditText)findViewById(R.id.editText_text_editor);
		mButtonEdit = (Button)findViewById(R.id.button_edit);
		mButtonExit = (Button)findViewById(R.id.button_exit);
		
		Intent extra = getIntent();
		String fn = extra.getStringExtra(OptionsActivity.PARAMS_FILENAME);
		mFile = new File(fn);
		
		// set title
		this.setTitle(fn);
		
		// read file
		String s = readFile(mFile);
		mText.setText(s);
		bEditable = false;
		
		mButtonEdit.setOnClickListener(this);
		mButtonExit.setOnClickListener(this);
	}

	private String readFile(File file) {
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
	
	private void writeFile(File file, String data) {
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_edit:
			bEditable = true;
			break;
		case R.id.button_exit:
			if (bEditable) {
				DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface diaglog, int which) {
						//String s = mText.getText().toString();
						//if (s.length() > 0)
						//	writeFile(mFile, s);
						Toast.makeText(getBaseContext(), "File saved", Toast.LENGTH_SHORT).show();
						finish();
					}
				};
				DialogInterface.OnClickListener cancelButtonListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};
				new AlertDialog.Builder(this)
				.setTitle("Save?")
				.setNeutralButton("OK", okButtonListener)
				.setNegativeButton("Cancel", cancelButtonListener)
				.setIcon(R.drawable.folder)
				.show();
			}
			break;
		}
	}
}
