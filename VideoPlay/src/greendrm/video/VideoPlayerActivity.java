package greendrm.video;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        VideoView video = (VideoView)findViewById(R.id.videoView1);
        File sdcard = new File(Environment.getExternalStorageDirectory(), "Heartbeat-applegirl.mp4");
        
        video.setVideoPath(sdcard.toString());
        video.start();
    }
}