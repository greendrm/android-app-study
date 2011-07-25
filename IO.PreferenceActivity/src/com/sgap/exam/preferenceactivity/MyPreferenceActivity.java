package com.sgap.exam.preferenceactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MyPreferenceActivity extends Activity  {

    private TextView checkbox;
    private TextView added;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkbox = (TextView)findViewById(R.id.checkbox);
        added =(TextView)findViewById(R.id.tx_added);
        
    }
    @Override
    protected void onResume() {
        super.onResume();
        
        retrivePreferences();
        
    }
    private void retrivePreferences() {
        SharedPreferences prefs = PreferenceManager.
            getDefaultSharedPreferences(getApplicationContext());
        
        String color = prefs.getString("textColor", "#FFFFFF");
        String msg = prefs.getString("newMsg", "<NONE>");
        int size = Integer.parseInt( prefs.getString("msgSize", "10") );
        boolean check = prefs.getBoolean("checkbox", false);

        checkbox.setText( new Boolean(check).toString());
        added.setText( msg );
        added.setTextColor(Color.parseColor(color));
        added.setTextSize(size);
    }
    
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 설정값
        retrivePreferences();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Create and add new menu items.
        MenuItem itemAdd = menu.add(0, 0, Menu.NONE, "설정");
        MenuItem itemRem = menu.add(0, 1, Menu.NONE, "종료");

        // Assign icons
        itemAdd.setIcon(android.R.drawable.ic_menu_preferences);
        itemRem.setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, EditPreferenceActivity.class));
                return true;
            case 1:
                finish();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }
    
}
