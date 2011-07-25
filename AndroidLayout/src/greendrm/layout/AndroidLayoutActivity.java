package greendrm.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AndroidLayoutActivity extends Activity implements OnClickListener {
	LinearLayout layout1;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout1.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getBaseContext(), SecondActivity.class);
		startActivity(intent);
	}
}