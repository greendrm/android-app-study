package greendrm.imageview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.util.Log;

public class AndroidImageViewActivity extends Activity {
	private static final String TAG = "DJK";
	ImageView image;
	SeekBar seek;
	Boolean bToggle = true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnChange = (Button)findViewById(R.id.button1);
        Button btnAlpha = (Button)findViewById(R.id.button2);
        image = (ImageView)findViewById(R.id.image);
        seek = (SeekBar)findViewById(R.id.seekBar1);
        
        //seek.setProgress(0);
        
		btnChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bToggle) {
					bToggle = false;
					image.setImageResource(R.drawable.android_wallpaper_2);
				}
				else {
					bToggle = true;
					image.setImageResource(R.drawable.android_wallpaper);
				}
			}
		});
		
		btnAlpha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				image.setAlpha(50);
				seek.setProgress(50);
			}
		});
		
		seek.setOnSeekBarChangeListener(
				new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						//Log.d(TAG, ""+progress);
						image.setAlpha(progress);
					}
				});
    }
}