package greendrm.pickimage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;

public class AndroidPickImageActivity extends Activity {
	private final int PICK_FROM_ALBUM = 0;
	ImageView image;
	SeekBar seek;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView)findViewById(R.id.imageView1);
        image.setOnLongClickListener(new OnLongClickListener() {
        	@Override
        	public boolean onLongClick(View v) {
        		Intent intent = new Intent(Intent.ACTION_PICK);
        		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        		startActivityForResult(intent, PICK_FROM_ALBUM);
        		return false;
        	}
        });
        
        seek = (SeekBar)findViewById(R.id.seekBar1);
        seek.setMax(256);
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
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_FROM_ALBUM) {
				Uri uri = data.getData();
				image.setImageURI(uri);
			}
		}
	}
}