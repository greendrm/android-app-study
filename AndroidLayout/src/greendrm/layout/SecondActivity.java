package greendrm.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SecondActivity extends Activity implements OnClickListener {
	LinearLayout layout2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
        layout2 = (LinearLayout)findViewById(R.id.layout2);
        layout2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getBaseContext(), ThirdActivity.class);
		startActivity(intent);
	}
}
