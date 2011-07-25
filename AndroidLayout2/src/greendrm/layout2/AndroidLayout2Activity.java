package greendrm.layout2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AndroidLayout2Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        LinearLayout layout = new LinearLayout(this);
        TextView text = new TextView(this);
        text.setText(R.string.hello);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(text);
        
        LinearLayout insideLayout = new LinearLayout(this);
        insideLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button button1 = new Button(this);
        Button button2 = new Button(this);
        Button button3 = new Button(this);
        insideLayout.addView(button1);
        insideLayout.addView(button2);
        insideLayout.addView(button3);
        
        layout.addView(insideLayout);
        
        setContentView(layout);
    }
}