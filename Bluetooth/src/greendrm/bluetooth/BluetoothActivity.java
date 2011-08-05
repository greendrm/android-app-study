package greendrm.bluetooth;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class BluetoothActivity extends Activity {

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;
	
	private EditText etView;
	private BluetoothAdapter mBTAdapter;
	private ArrayAdapter<String> mArrayAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        etView = (EditText)findViewById(R.id.editText1);
        mArrayAdapter = new ArrayAdapter<String>(this, 0);
        
        etView.append("Start\n");
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBTAdapter == null) {
        	etView.append("Warning: device doest not support Bluetooth\n");
        }
        
        // enable the BT
        if (mBTAdapter != null && !mBTAdapter.isEnabled()) {
        	etView.append("try to enable BT...\n");
        	Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        	startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        
        // query about paired devices
        etView.append("Paired Device\n---------------\n");
        Set<BluetoothDevice> pairedDevice = mBTAdapter.getBondedDevices();
        if (pairedDevice.size() != 0) {
        	for (BluetoothDevice device : pairedDevice) {
        		etView.append(device.getName()+ "\n" + device.getAddress() + "\n");
        	}
        }
        
        
        // register
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        
        mBTAdapter.startDiscovery();
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				etView.append("BT Enabled!\n");
				mBTAdapter.startDiscovery();
			}
			else {
				etView.append("Trying to enable BT is failed\n");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device =
					intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				etView.append("Discovered Device\n---------------\n");
				etView.append(device.getName()+ "\n" + device.getAddress() + "\n");
			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				etView.append("Discoverd finished\n");
			}
		}
	};
}