package com.greendrm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.*;

public class HelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	long start = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView t1 = (TextView) findViewById(R.id.TextView1);
        
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
		
		t1.setText(account1.ownerName + " 예금 잔액:" + account1.getBalance() + "\n" +
			account2.ownerName + " 예금 잔액:" + account2.getBalance() + "\n" +
			bonus1.ownerName + " 예금 잔액:" + bonus1.getBalance() + " 보너스:" + bonus1.getBonus());
	
		// textView2
		TextView t2 = (TextView) findViewById(R.id.textView2);
		Runtime rt = Runtime.getRuntime();
		t2.setText(rt.freeMemory() / 1024 + " KB" + " / " + rt.totalMemory() / 1024 + " KB");
		
		long end = System.currentTimeMillis();
		
		// TextView3
		TextView t3 = (TextView) findViewById(R.id.textView3);
		t3.setText("Elapsed Time: " + (end - start));
		
		// Log
		final TextView tLog = (TextView)findViewById(R.id.log);
		Button btn_print = (Button)findViewById(R.id.button1);
		
		btn_print.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tLog.setText(account1.ownerName + " 예금 잔액:" + account1.getBalance() + "\n" +
						account2.ownerName + " 예금 잔액:" + account2.getBalance() + "\n" +
						bonus1.ownerName + " 예금 잔액:" + bonus1.getBalance() + " 보너스:" + bonus1.getBonus());
			}
		});
    }
}