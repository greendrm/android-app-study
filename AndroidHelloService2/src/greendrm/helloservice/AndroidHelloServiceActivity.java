package greendrm.helloservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroidHelloServiceActivity extends Activity implements OnClickListener {
	private TestService.TestBinder binder;
	private TestServiceConnection serviceConnection;
	
	Button buttonBinder;
	Button buttonAdd, buttonSub, buttonMul, buttonDiv;
	EditText editOp1, editOp2;
	TextView textviewResult;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonBinder = (Button)findViewById(R.id.buttonBinder);
        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonSub = (Button)findViewById(R.id.buttonSub);
        buttonMul = (Button)findViewById(R.id.buttonMul);
        buttonDiv = (Button)findViewById(R.id.buttonDiv);
        
        editOp1 = (EditText)findViewById(R.id.editTextOp1);
        editOp2 = (EditText)findViewById(R.id.editTextOp2);
        
        textviewResult = (TextView)findViewById(R.id.textViewResult);
        
        buttonBinder.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View v) {
		if (v == buttonBinder) {
			if (binder == null) {
				serviceConnection = new TestServiceConnection();
				Intent intent = new Intent(this, TestService.class);
				if (bindService(intent, serviceConnection,
						Context.BIND_AUTO_CREATE)) {
					buttonBinder.setText(getResources().getText(R.string.binderUnbind));
				}
				else {
					binder = null;
				}
			}
			else {
				unbindService(serviceConnection);
				binder = null;
				buttonBinder.setText(getResources().getText(R.string.binderService));
			}
		}
		else if (v == buttonAdd) {
			if (binder != null) {
				Integer a, b;
				try {
					a = new Integer(editOp1.getText().toString());
					b = new Integer(editOp2.getText().toString());
				}
				catch (NumberFormatException e) {
					return;
				}
				Integer res;
				res = binder.add(a, b);
				
				textviewResult.setText(res.toString());
			}
		}
		else if (v == buttonSub) {
			if (binder != null) {
				Integer a, b;
				try {
					a = new Integer(editOp1.getText().toString());
					b = new Integer(editOp2.getText().toString());
				}
				catch (NumberFormatException e) {
					return;
				}
				Integer res;
				res = binder.sub(a, b);
				
				textviewResult.setText(res.toString());
			}
		}
		else if (v == buttonMul) {
			Integer a, b;
			if (binder != null) {
				try {
					a = new Integer(editOp1.getText().toString());
					b = new Integer(editOp2.getText().toString());
				}
				catch (NumberFormatException e) {
					return;
				}
				Integer res;
				res = binder.mul(a, b);
				
				textviewResult.setText(res.toString());
			}
		}
		else if (v == buttonDiv) {
			if (binder != null) {
				Double a, b;
				try {
					a = new Double(editOp1.getText().toString());
					b = new Double(editOp2.getText().toString());
				}
				catch (NumberFormatException e) {
					return;
				}
				Double res;
				res = binder.div(a, b);
				
				textviewResult.setText(res.toString());
			}
		}
	}
	
    private class TestServiceConnection implements ServiceConnection {
    	@Override
    	public void onServiceConnected(ComponentName name, IBinder _binder) {
    		binder = (TestService.TestBinder)_binder;
    	}
    	
    	@Override
    	public void onServiceDisconnected(ComponentName name) {
    		binder = null;
    	}
    }
}