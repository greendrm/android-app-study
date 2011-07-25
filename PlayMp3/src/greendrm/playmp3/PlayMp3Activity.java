package greendrm.playmp3;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class PlayMp3Activity extends Activity {
	MediaPlayer audio_play = null;
	String sampleMp3 = 
		"http://www.archive.org/download/SteveJobsSpeechAtStanfordUniversity/SteveJobsSpeech_64kb.mp3";
	SeekBar seekbar;
	Thread thread;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        seekbar = (SeekBar)findViewById(R.id.seekBar1);
        UpdateProgress updateProgress = new UpdateProgress();
        thread = new Thread(updateProgress);
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
    		seekbar.setProgress(0);
    		audio_play.stop();
    		audio_play.release();
    	}
    }
    
    public void playMp3(int id) {
    	switch (id) {
    	case 0:
    		audio_play = MediaPlayer.create(getBaseContext(), Uri.parse(sampleMp3));
    		if (!audio_play.isPlaying()) {
    			seekbar.setMax(audio_play.getDuration());
    			audio_play.start();
    			thread.start();
    		}
    		break;
    	case 1:
    		AssetFileDescriptor afd;
    		audio_play = new MediaPlayer();
    		try {
    			afd = getAssets().openFd("three_bears.mp3");
    			audio_play.setDataSource(afd.getFileDescriptor(),
    					afd.getStartOffset(), afd.getLength());
    			audio_play.prepare();
    			seekbar.setMax(audio_play.getDuration());
    			audio_play.start();
    			thread.start();
    			afd.close();
    		} 
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    		break;
    	case 2:
    		audio_play = MediaPlayer.create(getBaseContext(),
    				R.raw.three_bears);
    		seekbar.setMax(audio_play.getDuration());
    		audio_play.start();
    		thread.start();
    		break;
    	}
    }
    
    class UpdateProgress implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				if (audio_play != null) {
					while (audio_play.isPlaying()) {
						seekbar.setProgress(audio_play.getCurrentPosition());
						try {
							Thread.sleep(500);
						}
						catch (Exception e){
							//
						}
					}
					seekbar.setProgress(0);
				}
			} catch (Exception e) {
				//
			}
		}
    	
    }
}