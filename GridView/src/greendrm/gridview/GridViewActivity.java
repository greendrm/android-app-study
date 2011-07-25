package greendrm.gridview;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class GridViewActivity extends Activity {
	GridView gridview;
	ArrayList<GridViewContactsBook> contacts;
	ContactsAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gridview = (GridView)findViewById(R.id.gridView1);
        contacts = new ArrayList<GridViewContactsBook>();
        
        contacts.add(new GridViewContactsBook("A", "000-000-0000"));
        contacts.add(new GridViewContactsBook("B", "111-000-0000"));
        contacts.add(new GridViewContactsBook("C", "222-000-0000"));
        contacts.add(new GridViewContactsBook("D", "333-000-0000"));
        contacts.add(new GridViewContactsBook("E", "444-000-0000"));
        contacts.add(new GridViewContactsBook("F", "555-000-0000"));
        contacts.add(new GridViewContactsBook("G", "666-000-0000"));
        contacts.add(new GridViewContactsBook("H", "777-000-0000"));
        contacts.add(new GridViewContactsBook("I", "888-000-0000"));
        adapter = new ContactsAdapter(contacts);
        gridview.setAdapter(adapter);
        
        gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GridViewContactsBook obj = contacts.get(position);
				if (obj.isSelected()) {
					obj.setSelection(false);
					view.setBackgroundColor(Color.TRANSPARENT);
				} else {
					obj.setSelection(true);
					view.setBackgroundColor(Color.YELLOW);
				}
			}
        });
    }
    
    class ContactsAdapter extends BaseAdapter {
    	private ArrayList<GridViewContactsBook> obj;
    	
    	public ContactsAdapter(ArrayList<GridViewContactsBook> obj) {
    		super();
    		this.obj = obj;
    	}

		@Override
		public int getCount() {
			return obj.size();
		}

		@Override
		public Object getItem(int position) {
			return obj;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(GridViewActivity.this);
				convertView = inflater.inflate(R.layout.listrow, parent, false);
				holder = new ViewHolder();
				holder.txtName = (TextView)convertView.findViewById(R.id.textView1);
				holder.txtNumber = (TextView)convertView.findViewById(R.id.textView2);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			String name = obj.get(position).getName();
			String phonenumber = obj.get(position).getPhoneNumber();
			holder.txtName.setText(name);
			holder.txtNumber.setText(phonenumber);

			return convertView;
		}
    }
    
    class ViewHolder {
    	TextView txtName;
    	TextView txtNumber;
    }
}