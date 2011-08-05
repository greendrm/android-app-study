package greendrm.bluetooth.server;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;

public class BluetoothServerActivity extends Activity {
	public static final int MESSAGE_READ = 1;
	
	private static final String TAG = "BluetoothServer";
	private static final boolean DEBUG = true;
	
	private static final UUID MY_UUID = UUID.fromString("000011008-0000-1000-8000-00805F9B34FB");
	private static final int REQUEST_ENABLE_BT = 3;
	
	private BluetoothAdapter mBTAdapter;
	private AcceptThread mAcceptThread;
	private EditText view;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        view = (EditText)findViewById(R.id.editText1);
        view.setGravity(Gravity.TOP | Gravity.LEFT);
        
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBTAdapter == null) {
        	Log.i(TAG, "device does not support Bluetooth");
        }
    }
    
	@Override
	protected void onStart() {
		super.onStart();
		
		if (!mBTAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		else {
			setDiscoveralbe();
		}
	}
    
	@Override
	protected void onPause() {
		mAcceptThread.cancel();
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		mAcceptThread = new AcceptThread();
		mAcceptThread.start();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				view.append("Enabled BT\n");
				
				setDiscoveralbe();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private class AcceptThread extends Thread {
		private final BluetoothServerSocket mmServerSocket;
		
		public AcceptThread() {
			BluetoothServerSocket tmp = null;
			try {
				tmp = mBTAdapter.listenUsingRfcommWithServiceRecord("BluetoothServer", MY_UUID);
			}
			catch (IOException e) { }
			mmServerSocket = tmp;
		}
		
		@Override
		public void run() {
			BluetoothSocket socket = null;
			while (true) {
				try {
					socket = mmServerSocket.accept();
				}
				catch (IOException e) { 
					break;
				}
				
				if (socket != null) {
					manageConnectedSocket(socket);
					try {
						mmServerSocket.close();
					} catch (IOException e) {
					}
					break;
				}
			}
		}
		
		public void cancel() {
			try {
				mmServerSocket.close();
			}
			catch (IOException e) { }
		}
	}
	
	public void manageConnectedSocket(BluetoothSocket socket) {
		if (DEBUG) Log.d(TAG, "Connected");
		ConnectedThread server = new ConnectedThread(mHandler, socket);
		server.start();
	}
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_READ:
				byte[] readBuf = (byte[])msg.obj;
				String readMessage = new String(readBuf, 0, msg.arg1);
				view.append(readMessage);
			}
		}
	};
	
	private void setDiscoveralbe() {
		view.append("set BT as being discoverable during 2 minutes\n");
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120); // 2 minutes
		startActivity(discoverableIntent);
	}
}