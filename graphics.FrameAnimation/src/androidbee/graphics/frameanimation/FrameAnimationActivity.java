
package androidbee.graphics.frameanimation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class FrameAnimationActivity extends Activity implements OnClickListener {
    private ImageView imageFrame;

    private Button buttonFrameStart;

    private Button buttonFrameStop;

    private Button buttonAlpha;

    private Button buttonRotation;

    private Button buttonScale;

    private Button buttonTranslate;

    private Button buttonSet;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        imageFrame = (ImageView) findViewById(R.id.image_frame);
        buttonFrameStart = (Button) findViewById(R.id.button_frame_start);
        buttonFrameStop = (Button) findViewById(R.id.button_frame_stop);
        buttonFrameStart.setOnClickListener(this);
        buttonFrameStop.setOnClickListener(this);

        imageFrame = (ImageView) findViewById(R.id.image_frame);
        buttonAlpha = (Button) findViewById(R.id.button1);
        buttonRotation = (Button) findViewById(R.id.button2);
        buttonScale = (Button) findViewById(R.id.button3);
        buttonTranslate = (Button) findViewById(R.id.button4);
        buttonSet = (Button) findViewById(R.id.button5);

        buttonAlpha.setOnClickListener(this);
        buttonRotation.setOnClickListener(this);
        buttonScale.setOnClickListener(this);
        buttonTranslate.setOnClickListener(this);
        buttonSet.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == buttonFrameStart) {
            AnimationDrawable drawableFrame = (AnimationDrawable) imageFrame.getDrawable();
            drawableFrame.start();
        } else if (v == buttonFrameStop) {
            AnimationDrawable drawableFrame = (AnimationDrawable) imageFrame.getDrawable();
            drawableFrame.stop();
        } else if (v == buttonAlpha) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
            imageFrame.startAnimation(animation);
        } else if (v == buttonRotation) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            imageFrame.startAnimation(animation);
        } else if (v == buttonScale) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
            imageFrame.startAnimation(animation);
        } else if (v == buttonTranslate) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
            imageFrame.startAnimation(animation);
        } else if (v == buttonSet) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.set);
            imageFrame.startAnimation(animation);
        }
    }
}
