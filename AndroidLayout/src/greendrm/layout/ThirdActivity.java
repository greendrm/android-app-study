package greendrm.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;


public class ThirdActivity extends Activity implements OnClickListener {
	LinearLayout layout3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third);
		
        layout3 = (LinearLayout)findViewById(R.id.layout3);
        layout3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
//		Intent intent = new Intent(getBaseContext(), AndroidLayoutActivity.class);
//		startActivity(intent);
	}

}
