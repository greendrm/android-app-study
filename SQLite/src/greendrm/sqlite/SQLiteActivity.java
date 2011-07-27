package greendrm.sqlite;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class SQLiteActivity extends ListActivity {
	private static final boolean DEBUG = true;
	private static final String TAG = "SQLite";
	
	private DatabaseHelper mOpenHelper;
	private ArrayList<Person> item = null;
	PersonAdapter mAdapter;
	private Person mPerson = null;
	
	private static String DATABASE_NAME = "testdb.db";
	private static String DATABASE_TABLE = "test_table";
	private static int DATABASE_VERSION = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = new ArrayList<Person>();
        mOpenHelper = new DatabaseHelper(this);
        mOpenHelper.retriveData();
        mAdapter = new PersonAdapter(item);
        setListAdapter(mAdapter);
        
        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPerson = item.get(position);
				
				DialogInterface.OnClickListener deleteListener =
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mOpenHelper.deleteData(mPerson);
							item = mOpenHelper.retriveData();
							mAdapter.notifyDataSetChanged();
						}
				};
				
				DialogInterface.OnClickListener cancelListener =
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
				};
				
				new AlertDialog.Builder(SQLiteActivity.this)
					.setTitle("Delete?")
					.setNeutralButton("Delete", deleteListener)
					.setNegativeButton("Cancle", cancelListener)
					.show();
				
				return false;
			}
        	
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	//SQLiteDatabase db = mOpenHelper.getReadableDatabase();
    	if (Activity.RESULT_OK == resultCode) {
    		Bundle extra = data.getExtras();
    		Person person = new Person(extra.getString("firstname"),
    				extra.getString("lastname"), 
    				extra.getString("country"),
    				Integer.parseInt(extra.getString("age")));
    		mOpenHelper.insertData(person);
    		item = mOpenHelper.retriveData();
    		mAdapter.notifyDataSetChanged();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuItem itemAdd = menu.add(0, 0, Menu.NONE, "새로만들기");
    	MenuItem itemRem = menu.add(0, 1, Menu.NONE, "종료");
    	itemAdd.setIcon(android.R.drawable.ic_menu_add);
    	itemRem.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case 0:
    		startActivityForResult(new Intent(this, AddActivity.class), 0);
    		return true;
    	case 1:
    		finish();
    		return true;
    	}
    	return (super.onOptionsItemSelected(item));
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
    	public DatabaseHelper(Context context) {
    		super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	}
    	
    	@Override
    	public void onCreate(SQLiteDatabase db) {
    		db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + "_id INTEGER PRIMARY KEY,"
    				+ "lastname VARCHAR, " + "firstname VARCHAR, " + "country VARCHAR, "
    				+ "age INT(3));");
    		String insertMe = "INSERT INTO " + DATABASE_TABLE
    		+ "(lastname, firstname, country, age) " + "VALUES ";
    		db.execSQL(insertMe + "('홍','길동','대한민국', 20);");
    	}
    	
    	@Override
    	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    		onCreate(db);
    	}
    	public void insertData(Person person) {
    		SQLiteDatabase db = getWritableDatabase();
    		String insertMe = "INSERT INTO " + DATABASE_TABLE
    		+ "(lastname, firstname, country, age) " + "VALUES ";
    		db.execSQL(insertMe + "('" + person.lastname + "'," + "'"
    				+ person.firstname + "'," + "'" + person.country
    				+ "', " + person.age + ");");
    	}
    	
    	public void deleteData(Person person) {
    		SQLiteDatabase db = getReadableDatabase();
    		String deleteMe = "DELETE FROM " + DATABASE_TABLE;
    		db.execSQL(deleteMe + " WHERE " + 
    				"lastname=" + "'" + person.lastname + "'" + " AND " + 
    				"firstname="+ "'" + person.firstname + "'" + " AND " +
    				"country="+ "'" + person.country + "'" + " AND " +
    				"age="+ "'" + person.age + "'");
    	}
    	
    	public ArrayList<Person> retriveData() {
    		//List<String> item = null;
    		SQLiteDatabase db = getReadableDatabase();
    		Cursor c = db.query(DATABASE_TABLE, new String[] {"_id", "lastname", "firstname", "country", "age"}, null, null, null, null, null);
    		try {
    			item.clear();
    			int idColumn = c.getColumnIndex("_id");
    			int lastNameColumn = c.getColumnIndex("lastname");
    			int firstNameColumn = c.getColumnIndex("firstname");
    			int countryColumn = c.getColumnIndex("country");
    			int ageColumn = c.getColumnIndex("age");
    			int i = 0;
    			if (c.moveToFirst()) {
    				do {
    					i++;
    					int id = c.getInt(idColumn);
    					String firstName = c.getString(firstNameColumn);
    					String lastName = c.getString(lastNameColumn);
    					String country = c.getString(countryColumn);
    					int age = c.getInt(ageColumn);
    					//item.add("" + i + ":" + lastName + " " + firstName + "(" + country + ":"
    					//		+ age + ")");
    					item.add(new Person(firstName, lastName, country, age));
    					if (DEBUG) Log.d(TAG, id + ":" + lastName + " " + firstName + "(" + country + ":"
    							+ age + ")");
    				} while (c.moveToNext());
    			}
    		} finally {
    			if (c != null) c.close();
    		}
    		return item;
    	}
    }
    
    private class Person {
    	public String firstname;
    	public String lastname;
    	public String country;
    	int age;
    	
    	public Person(String firstname, String lastname, String country, int age) {
    		this.firstname = firstname;
    		this.lastname = lastname;
    		this.country = country;
    		this.age = age;
    	}
    }
    
    private class PersonAdapter extends BaseAdapter {
    	private ArrayList<Person> obj;

    	public PersonAdapter(ArrayList<Person> obj) {
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
				LayoutInflater inflater = LayoutInflater.from(SQLiteActivity.this);
				convertView = inflater.inflate(R.layout.basic_list, parent, false);
				holder = new ViewHolder();
				holder.txt = (TextView)convertView.findViewById(R.id.textViewPerson);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			String firstname = obj.get(position).firstname;
			String lastname = obj.get(position).lastname;
			String country = obj.get(position).country;
			int age = obj.get(position).age;
			String tmp = "" + lastname + " " + firstname + "(" + country + ":"
					+ age + ")";
			holder.txt.setText(tmp);
			return convertView;
		}
    	
		class ViewHolder {
			TextView txt;
		}
    }
}