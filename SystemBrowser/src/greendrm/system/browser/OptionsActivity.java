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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OptionsActivity extends Activity {
	private static final boolean DEBUG = true;
	private static final String TAG = "SystemBrowserOptions";
	
	public static final String PARAMS_FILENAME = "FILE_NAME";

	private File mFile;
	
	private TextView mTitle;
	private ListView mList;
	private ArrayList<String> options;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.systembrowser_options);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		
		Intent extra = getIntent();
		String fn = extra.getStringExtra(PARAMS_FILENAME);
		mFile = new File(fn);
		if (DEBUG) Log.d(TAG, "file name: " + fn);
		
		// Set up the custom title
		mTitle = (TextView)findViewById(R.id.textView_title);
		mTitle.setText(fn);
		
		mList = (ListView)findViewById(R.id.listView_options);
		options = new ArrayList<String>();
		options.add(getString(R.string.options_edit));
		//options.add(getString(R.string.options_rename));
		//options.add(getString(R.string.options_delete));
		options.add(getString(R.string.options_attribute));
		adapter = new ArrayAdapter<String>(this, R.layout.list_item_1, options);
		mList.setAdapter(adapter);
		
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos,
					long id) {
				TextView t = (TextView)v;
				
				if (t.getText().equals(getString(R.string.options_edit))) {
					Intent intent = new Intent(getBaseContext(), TextEditorActivity.class);
					intent.putExtra(PARAMS_FILENAME, mFile.getAbsolutePath());
					startActivity(intent);
					finish();
				}
				else if (t.getText().equals(getString(R.string.options_attribute))) {
					
				}
			}
		});
	}
}
