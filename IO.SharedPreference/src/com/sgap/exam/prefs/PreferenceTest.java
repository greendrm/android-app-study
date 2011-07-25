package com.sgap.exam.prefs;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class PreferenceTest extends Activity {
    final static String MYPREFERENCE = "MyPrefTest";
    
    TextView textName;
    TextView textId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_userid);

        textName = (TextView)findViewById(R.id.name);
        textId = (TextView)findViewById(R.id.stnum);

        SharedPreferences pref = getSharedPreferences( MYPREFERENCE, Context.MODE_PRIVATE);
        String _name = pref.getString("_NAME", "이름없음");
        textName.setText(_name);

        int _id = pref.getInt("_ID",0);
        textId.setText("" + _id);
    }

    public void onPause() {
        super.onPause();

        SharedPreferences pref = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        String _name = textName.getText().toString();
        int _id = 0;
        try {
            _id = Integer.parseInt(textId.getText().toString());
        }
        catch (Exception e) {}

        edit.putString("_NAME", _name);
        edit.putInt("_ID", _id);

        edit.commit();
    }
}
