package greendrm.listview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidHelloListViewActivity extends Activity {
	ListView list;
	ArrayList<Integer> numbers;
	ArrayAdapter<Integer> adapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.listView1);
        numbers = new ArrayList<Integer>();
        for (int i = 1; i < 11; i++) {
        	numbers.add(i);
        }
        adapter = new ArrayAdapter<Integer>(this, R.layout.simple_list_item_1, numbers);
        list.setAdapter(adapter);
    }
}