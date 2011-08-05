package greendrm.bluethooth.client;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DeviceListActivity extends Activity {
	private static final String TAG = "DeviceList";
	
	// Return Intent extra
    private static final String EXTRA_DEVICE_ADDRESS = "device_address";
	
	private ListView mList;
	private ArrayList<DeviceList> devices;
	private DeviceAdapter adapter;
	
	private BluetoothAdapter mBTAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.devicelist);
		
		// Set result CANCELED when user backs out
        setResult(Activity.RESULT_CANCELED);
		
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBTAdapter == null) {
        	Log.i(TAG, "device does not support Bluetooth");
        	finish();
        }
        
		mList = (ListView)findViewById(R.id.listView1);
		
		devices = new ArrayList<DeviceList>();
		adapter = new DeviceAdapter(devices);
        mList.setAdapter(adapter);
        
        mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DeviceList device = devices.get(position);
				
				 // Create the result Intent and include the MAC address
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DEVICE_ADDRESS, device.getAddress());

                // Set result and finish this Activity
                setResult(Activity.RESULT_OK, intent);
                finish();
			}
        });
        
        
        IntentFilter filter = new IntentFilter();
        // Register for broadcasts when a device is discovered
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        // Register for broadcasts when discovery has finished
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
        
        setProgressBarIndeterminateVisibility(true);
        setTitle("Bluetooth Devices");
        // Paired Bluetooth
        Set<BluetoothDevice> pairedDevice = mBTAdapter.getBondedDevices();
        if (pairedDevice.size() != 0) {
        	for (BluetoothDevice device: pairedDevice) {
        		devices.add(new DeviceList(device.getName(), device.getAddress()));
        	}
        }
        // Request discover from BluetoothAdapter
        mBTAdapter.startDiscovery();
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBTAdapter != null) {
            mBTAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(new DeviceList(device.getName(), device.getAddress()));
                adapter.notifyDataSetChanged();
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
            }
        }
    };
    
	class DeviceAdapter extends BaseAdapter {
    	private ArrayList<DeviceList> object;
    	
    	public DeviceAdapter(ArrayList<DeviceList> object) {
    		super();
    		this.object = object;
    	}

		@Override
		public int getCount() {
			return object.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
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
				LayoutInflater inflater = LayoutInflater.from(DeviceListActivity.this);
				convertView = inflater.inflate(R.layout.list2, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView)convertView.findViewById(R.id.imageView1);
				holder.txtName = (TextView)convertView.findViewById(R.id.textView1);
				holder.txtAddress = (TextView)convertView.findViewById(R.id.textView2);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			String name = object.get(position).getName();
			String address = object.get(position).getAddress();
			//int image = object.get(position).getImage();
			//holder.image.setImageResource(image);
			holder.txtName.setText(name);
			holder.txtAddress.setText(address);
			return convertView;
		}
    }
    
    static class ViewHolder {
    	ImageView image;
    	TextView txtName;
    	TextView txtAddress;
    }

    private class DeviceList {
		private String mmName;
    	private String mmAddress;
    	
    	public DeviceList() { }
    	public DeviceList(String name, String address) {
    		mmName = name;
    		mmAddress = address;
    	}
    	public String getName() {
			return mmName;
		}
		public void setName(String name) {
			this.mmName = name;
		}
		public String getAddress() {
			return mmAddress;
		}
		public void setAddress(String address) {
			this.mmAddress = address;
		}
    }
}
