package greendrm.listview2;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListView2Activity extends Activity {
	ListView list;
	ArrayList<ListViewContactsBook> contacts;
	ContactsAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.listView1);
        
        contacts = new ArrayList<ListViewContactsBook>();
        contacts.add(new ListViewContactsBook(
        		R.drawable.tiffany,
        		"Tiffany", "011-111-1111"));
        contacts.add(new ListViewContactsBook(
        		R.drawable.sunny,
        		"Sunny", "011-222-2222"));
        contacts.add(new ListViewContactsBook(
        		R.drawable.yoona,
        		"Yoona", "011-333-3333"));
        adapter = new ContactsAdapter(contacts);
        list.setAdapter(adapter);
    }
    
    class ContactsAdapter extends BaseAdapter {
    	private ArrayList<ListViewContactsBook> object;
    	
    	public ContactsAdapter(ArrayList<ListViewContactsBook> object) {
    		super();
    		this.object = object;
    	}

		@Override
		public int getCount() {
			return object.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
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
				LayoutInflater inflater = LayoutInflater.from(ListView2Activity.this);
				convertView = inflater.inflate(R.layout.basic_list, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView)convertView.findViewById(R.id.imageView1);
				holder.txtName = (TextView)convertView.findViewById(R.id.textView1);
				holder.txtNumber = (TextView)convertView.findViewById(R.id.textView2);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			String name = object.get(position).getName();
			String phonenumber = object.get(position).getPhoneNumber();
			int image = object.get(position).getImage();
			holder.image.setImageResource(image);
			holder.txtName.setText(name);
			holder.txtNumber.setText(phonenumber);
			return convertView;
		}
    }
    
    static class ViewHolder {
    	ImageView image;
    	TextView txtName;
    	TextView txtNumber;
    }
}