package com.greendrm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.view.*;

public class HelloWorldActivity extends Activity {
//	int Count;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	long start = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String strBalance = getResources().getString(R.string.balance);
        TextView tvAccount = (TextView) findViewById(R.id.TextView1);
        TextView tvSystemInfo = (TextView) findViewById(R.id.textView2);
		TextView tvElasedTime = (TextView) findViewById(R.id.textView3);
		final TextView tvLog = (TextView)findViewById(R.id.log);
        
        final Account account1 = new Account("111-1111-1111", "김도집", 10000000);
		final Account account2 = new Account("222-2222-2222", "배용준", 100000000);
		final BonusAccount bonus1 = new BonusAccount("333-3333-3333", "김태희", 1000000, 100);
		
		account1.deposit(10000);
		try {
			account2.withdraw(100000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		bonus1.deposit(100);
		
		tvAccount.setText(account1.ownerName + " " + strBalance + ": " + account1.getBalance() + "\n" +
			account2.ownerName + " " + strBalance + ": " + account2.getBalance() + "\n" +
			bonus1.ownerName + " " + strBalance + ": " + bonus1.getBalance() + " 보너스:" + bonus1.getBonus());
	
		// textView2
		Runtime rt = Runtime.getRuntime();
		tvSystemInfo.setText(rt.freeMemory() / 1024 + " KB" + " / " + rt.totalMemory() / 1024 + " KB");
		
		long end = System.currentTimeMillis();
		
		// TextView3
		tvElasedTime.setText("Elapsed Time: " + (end - start));
		
		// Log
//		Count = 0;
		Button btnPrint = (Button)findViewById(R.id.button1);
		btnPrint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "하하하하하\nClick " + Count++, Toast.LENGTH_LONG).show();
				tvLog.setText(account1.ownerName + " 예금 잔액:" + account1.getBalance() + "\n" +
						account2.ownerName + " 예금 잔액:" + account2.getBalance() + "\n" +
						bonus1.ownerName + " 예금 잔액:" + bonus1.getBalance() + " 보너스:" + bonus1.getBonus());
			}
		});
		Button btnClear = (Button)findViewById(R.id.button2);
		btnClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvLog.setText("");
			}
		});
    }
}