package greendrm.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class SQLiteActivity extends ListActivity {
	private DatabaseHelper mOpenHelper;
	private List<String> item = null;
	ArrayAdapter<String> mAdapter;
	
	private static String DATABASE_NAME = "testdb.db";
	private static String DATABASE_TABLE = "test_table";
	private static int DATABASE_VERSION = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = new ArrayList<String>();
        mOpenHelper= new DatabaseHelper(this);
        mOpenHelper.retriveData();
        mAdapter=
        	new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
        setListAdapter(mAdapter);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	SQLiteDatabase db = mOpenHelper.getReadableDatabase();
    	if (Activity.RESULT_OK == resultCode) {
    		Bundle extra = data.getExtras();
    		String insertMe = "INSERT INTO " + DATABASE_TABLE
    		+ "(lastname, firstname, country, age) " + "VALUES ";
    		db.execSQL(insertMe + "('" + extra.getString("lastname") + "'," + "'"
    				+ extra.getString("firstname") + "'," + "'" + extra.getString("country")
    				+ "', " + extra.getString("age") + ");");
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
    	
    	public List<String> retriveData() {
    		//List<String> item = null;
    		SQLiteDatabase db = getReadableDatabase();
    		Cursor c = db.query(DATABASE_TABLE, new String[] {"lastname", "firstname", "country", "age"}, null, null, null, null, null);
    		try {
    			item.clear();
    			int lastNameColumn = c.getColumnIndex("lastname");
    			int firstNameColumn = c.getColumnIndex("firstname");
    			int countryColumn = c.getColumnIndex("country");
    			int ageColumn = c.getColumnIndex("age");
    			int i = 0;
    			if (c.moveToFirst()) {
    				do {
    					i++;
    					String firstName = c.getString(firstNameColumn);
    					String lastName = c.getString(lastNameColumn);
    					String country = c.getString(countryColumn);
    					int age = c.getInt(ageColumn);
    					item.add("" + i + ":" + lastName + " " + firstName + "(" + country + ":"
    							+ age + ")");
    				} while (c.moveToNext());
    			}
    		} finally {
    			if (c != null) c.close();
    		}
    		return item;
    	}
    }
}