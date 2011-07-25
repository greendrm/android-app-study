package greendrm.hello.radiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class AndroidHelloRadioButtonActivity extends Activity {
	RadioGroup group;
	TextView text;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text = (TextView)findViewById(R.id.textView1);
        group = (RadioGroup)findViewById(R.id.radioGroup1);
        group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radiobutton = (RadioButton)findViewById(checkedId);
				String str = radiobutton.getText().toString();
				text.setText(str);
			}
        });
        
    }
}