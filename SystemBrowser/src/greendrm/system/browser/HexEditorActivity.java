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
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HexEditorActivity extends Activity implements OnClickListener {
	private static final boolean DEBUG = true;
	private static final String TAG = "SystemBrowserHex";

	private File mFile;
	private ListView mListHex;
	
	private Button mButtonSave;
	private Button mButtonExit;
	
	List<AddressedText> mEntryHex;
	
	AddressedTextListAdapter mAdapterHex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hex_editor);
		
		Intent extra = getIntent();
		String fn = extra.getStringExtra(OptionsActivity.PARAMS_FILENAME);
		mFile = new File(fn);
		
		mButtonSave = (Button)findViewById(R.id.button_hex_save);
		mButtonExit = (Button)findViewById(R.id.button_hex_exit);
		
		// set title
		this.setTitle(fn);
		
		mListHex = (ListView)findViewById(R.id.listView_hex);
		
		mEntryHex = new ArrayList<AddressedText>();
		
		mAdapterHex = new AddressedTextListAdapter(this);
		mAdapterHex.setListItems(mEntryHex);
    	mListHex.setAdapter(mAdapterHex);
    	
    	String s = readFile(mFile);
    	fill(s);
    	
    	mButtonSave.setOnClickListener(this);
		mButtonExit.setOnClickListener(this);
	}
	
	private void fill(String s) {
		mEntryHex.clear();
		
		if (s == null)
			return;
		
		long size = s.length();
		
		// FIXME: limit the size
		if (size > 100)
			size = 100;
		
		for (int i = 0; i < size; i += 8) {
			AddressedText at_hex = new AddressedText();
			byte[] bytes = s.getBytes();
			String _s_asc = "";
			String _s_hex = "";
			long k;
			if ((size - i) < 8)
				k = size - i;
			else
				k = 8;
			for (int j = 0; j < k; j++) {
				byte c = bytes[i+j];
				_s_hex += String.format("%02x ", c);
				if (c < 32 || c > 126)
					c = '.';
				_s_asc += String.format("%c  ", c);
			}
			at_hex.setAddress(String.format("%08X", i));
			at_hex.setHex(_s_hex);
			at_hex.setAsc(_s_asc);
			
			mEntryHex.add(at_hex);
		}
		mAdapterHex.notifyDataSetChanged();
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
	
	private boolean writeFile(File file, String data) {
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
			Toast.makeText(this, "Can't write!", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "saveFile file is not exits or writable");
			return false;
		}
		
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_hex_save:
			Toast.makeText(this, "Not yet support", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button_hex_exit:
			finish();
			break;
		}
	}
}
