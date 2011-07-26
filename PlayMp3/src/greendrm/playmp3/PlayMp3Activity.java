package greendrm.playmp3;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class PlayMp3Activity extends Activity {
	String TAG = "PlayMp3";
	MediaPlayer audio_play = null;
	String sampleMp3 = 
		"http://www.archive.org/download/SteveJobsSpeechAtStanfordUniversity/SteveJobsSpeech_64kb.mp3";
	SeekBar seekbar;
	Thread thread;
	boolean isPlaying = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        seekbar = (SeekBar)findViewById(R.id.seekBar1);
        UpdateProgress updateProgress = new UpdateProgress();
        thread = new Thread(updateProgress);
        thread.start();
    }
    
	@Override
	protected void onDestroy() {
		thread.stop();
		super.onDestroy();
	}
    
    public void onClickPlay(View v) {
    	switch (v.getId()) {
    	case R.id.buttonPlay1:
    		playMp3(0);
    		break;
    	case R.id.buttonPlay2:
    		playMp3(1);
    		break;
    	case R.id.buttonPlay3:
    		playMp3(2);
    		break;
    	}
    }
    
    public void onClickStop(View v) {
    	if (audio_play != null) {
    		isPlaying = false;
    		try {
    			audio_play.stop();
    			audio_play.release();
    		}
    		catch (Exception e) {
    			//
    		}
    	}
    }
    
    public void playMp3(int id) {
    	if (isPlaying)
    		return;
    		
    	switch (id) {
    	case 0:
    		audio_play = MediaPlayer.create(getBaseContext(), Uri.parse(sampleMp3));
    		audio_play.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					isPlaying = false;
				}
    			
    		});
    		if (!audio_play.isPlaying()) {
    			seekbar.setMax(audio_play.getDuration());
    			audio_play.start();
    			isPlaying = true;
    		}
    		break;
    	case 1:
    		AssetFileDescriptor afd;
    		audio_play = new MediaPlayer();
    		audio_play.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					isPlaying = false;
				}
    			
    		});
    		try {
    			afd = getAssets().openFd("three_bears.mp3");
    			audio_play.setDataSource(afd.getFileDescriptor(),
    					afd.getStartOffset(), afd.getLength());
    			audio_play.prepare();
    			seekbar.setMax(audio_play.getDuration());
    			audio_play.start();
    			isPlaying = true;
    			afd.close();
    		} 
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    		break;
    	case 2:
    		audio_play = MediaPlayer.create(getBaseContext(),
    				R.raw.three_bears);
    		audio_play.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					isPlaying = false;
				}
    			
    		});
    		Log.d(TAG, "duration: " + audio_play.getDuration());
    		seekbar.setMax(audio_play.getDuration());
    		audio_play.start();
    		isPlaying = true;
    		break;
    	}
    }
    
    class UpdateProgress implements Runnable {
		@Override
		public void run() {
			while (true) {
				if (audio_play != null) {
					if (isPlaying) {
						try {
							seekbar.setProgress(audio_play.getCurrentPosition());
						}
						catch (Exception e) {
							//
						}
					}
					else {
						seekbar.setProgress(0);
					}
				}
				
				try {
					Thread.sleep(500);
				}
				catch (Exception e){
					//
				}
			}
		}
    }
}