package greendrm.system.sysfs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.*;

public class SysfsBrowserActivity extends Activity {
	final String SYSFS_PATH = "/sys";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        File fileSysfs;
        
        TextView tvPath = (TextView)findViewById(R.id.TextView1);
        TextView tvLog = (TextView)findViewById(R.id.TextView2);
        

        fileSysfs = new File(SYSFS_PATH);

        // check exist
        if (!fileSysfs.exists()) {
        	tvPath.setText("PATH: File not found!" + " :" + SYSFS_PATH);
        	return;
        }
        
        // full path
        String strPath = fileSysfs.getPath();
        tvPath.setText("PATH: " + strPath);
        
        // listing
        String[] lists = fileSysfs.list();
        if (lists != null && lists.length > 0) {
        	String msg;
        	msg = "1 : " + lists[0] + "\n";
        	for (int i = 1; i < lists.length; i++) {
        		msg += (i + 1) + " : ";
        		msg += lists[i];
        		msg += '\n';
        	}
        	tvLog.setText("num of list :" + fileSysfs.list().length + "\n" +
        			msg);
        }
        
    }
}