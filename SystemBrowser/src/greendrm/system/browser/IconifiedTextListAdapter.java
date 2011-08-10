/* 
 * Copyright 2007 Steven Osborn
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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconifiedTextListAdapter extends BaseAdapter {

	/** Remember our context so we can use it when constructing views. */
	private Context mContext;

	private List<IconifiedText> mItems = new ArrayList<IconifiedText>();

	public IconifiedTextListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(IconifiedText it) { mItems.add(it); }

	public void setListItems(List<IconifiedText> lit) { mItems = lit; }

	/** @return The number of items in the */
	public int getCount() { return mItems.size(); }

	public Object getItem(int position) { return mItems.get(position); }

	public boolean areAllItemsSelectable() { return false; }

	public boolean isSelectable(int position) { 
		try{
			return mItems.get(position).isSelectable();
		}catch (IndexOutOfBoundsException aioobe){
			return super.isEnabled(position);
		}
	}

	/** Use the array index as a unique id. */
	public long getItemId(int position) {
		return position;
	}

	/** @param convertView The old view to overwrite, if one is passed
	 * @returns a IconifiedTextView that holds wraps around an IconifiedText */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.iconified_text_list, parent, false);
			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.imageView_icon);
			holder.text = (TextView)convertView.findViewById(R.id.textView_filename);
			convertView.setTag(holder);
		} else { // Reuse/Overwrite the View passed
			holder = (ViewHolder)convertView.getTag();
		}
		holder.icon.setImageDrawable(mItems.get(position).getIcon());
		holder.text.setText(mItems.get(position).getText());
		return convertView;
	}
	
	static class ViewHolder {
		ImageView icon;
		TextView text;
	}
}