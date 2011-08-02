package greendrm.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationActivity extends Activity {
	private final boolean DEBUG = true;
	private final String TAG = "Animation";
	ImageView image;
	boolean bAlpha = false;
	boolean bRotate = false;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView)findViewById(R.id.imageView1);
    }
    
    public void onClickButton(View v) {
    	switch(v.getId()) {
    	case R.id.buttonStart: {
    		AnimationDrawable drawableframe = (AnimationDrawable)image.getDrawable();
    		drawableframe.start();
    		break;
    	}
    	case R.id.buttonStop: {
    		AnimationDrawable drawableframe = (AnimationDrawable)image.getDrawable();
    		drawableframe.stop();
    		break;
    	}
    	case R.id.buttonAlpha: {
    		Button button = (Button)findViewById(R.id.buttonAlpha);
    		Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
    		if (bAlpha == false) {
    			bAlpha = true;
    			button.setText("Stop Alpha");
    			image.startAnimation(animation);
    		}
    		else {
    			bAlpha = false;
    			button.setText("Start Alpha");
    			animation.cancel();
    		}
    		break;
    	}
    	case R.id.buttonRotate: {
    		Button button = (Button)findViewById(R.id.buttonRotate);
    		Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    		image.startAnimation(animation);
    		if(DEBUG) Log.d(TAG, "Clicked ButtonRotate");
//    		if (bRotate == false) {
//    			bRotate = true;
//    			button.setText("Stop Rotate");
//    			image.startAnimation(animation);
//    		}
//    		else {
//    			bRotate = false;
//    			button.setText("Start Rotate");
//    			animation.cancel();
//    		}
    		break;
    	}
    	default:
    			break;
    	}
    }
}