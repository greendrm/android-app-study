package greendrm.system.browser;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class AddressedTextListAdapter extends BaseAdapter {
	/** Remember our context so we can use it when constructing views. */
	private Context mContext;
	
	private List<AddressedText> mItems = new ArrayList<AddressedText>();

	public AddressedTextListAdapter(Context context) {
		mContext = context;
	}
	
	public void addItem(AddressedText it) {
		mItems.add(it);
	}

	public void setListItems(List<AddressedText> lit) {
		mItems = lit;
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
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
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.addressed_text_list, parent, false);
			holder = new ViewHolder();
			holder.address = (TextView)convertView.findViewById(R.id.textView_address);
			holder.hex = (EditText)convertView.findViewById(R.id.editText_hex);
			holder.asc = (TextView)convertView.findViewById(R.id.textView_asc);
			convertView.setTag(holder);
		} else { // Reuse/Overwrite the View passed
			holder = (ViewHolder)convertView.getTag();
		}
		holder.address.setText(mItems.get(position).getAddress());
		holder.hex.setText(mItems.get(position).getHex());
		holder.asc.setText(mItems.get(position).getAsc());
		return convertView;
	}

	static class ViewHolder {
		TextView address;
		EditText hex;
		TextView asc;
	}
}
