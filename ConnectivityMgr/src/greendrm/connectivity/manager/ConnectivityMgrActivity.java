package greendrm.connectivity.manager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;

public class ConnectivityMgrActivity extends Activity {
	String service = Context.CONNECTIVITY_SERVICE;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText result = (EditText)findViewById(R.id.editText1);
        String sResult = " ";
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(service);
        NetworkInfo[] ani = connMgr.getAllNetworkInfo();
        for (NetworkInfo n : ani) {
        	sResult += (n.toString() + "\n\n");
        }
        NetworkInfo ni = connMgr.getActiveNetworkInfo();
        sResult += ("Active : \n" + ni.toString() + "\n");
        result.setText(sResult);
    }
}