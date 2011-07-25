package greendrm.layout.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class AndroidLayoutTabActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabhost = (TabHost)findViewById(R.id.tabhost);
        tabhost.setup();
        TabHost.TabSpec spec;
        spec = tabhost.newTabSpec("Clock");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Clock");
        tabhost.addTab(spec);
        
        spec = tabhost.newTabSpec("Button");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Button");
        tabhost.addTab(spec);
        tabhost.setCurrentTab(0);
    }
}