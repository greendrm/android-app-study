package greendrm.editor;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EditorActivity extends Activity {
	ListView list;
	ArrayList<String> files;
	ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.listView1);
        files = new ArrayList<String>();
        
        files.add("a.txt");
        files.add("b.txt");
        
        adapter = new ArrayAdapter<String>(this, R.layout.listrow, files);
        list.setAdapter(adapter);
    }
}