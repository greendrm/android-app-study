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
import android.widget.TextView;

public class PlayMp3Activity extends Activity {
	private static final boolean DEBUG = true;
	private static final String TAG = "PlayMp3";
	MediaPlayer audio_play = null;
	final String sampleMp3 = 
		"http://www.archive.org/download/SteveJobsSpeechAtStanfordUniversity/SteveJobsSpeech_64kb.mp3";
	SeekBar seekbar;
	TextView textviewStart, textviewStop;
	Thread thread;
	boolean isPlaying = false;
	boolean isThreadRunning = true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (DEBUG) Log.d(TAG, "onCreate()");
        
        seekbar = (SeekBar)findViewById(R.id.seekBar1);
        textviewStart = (TextView)findViewById(R.id.textViewStart);
        textviewStop = (TextView)findViewById(R.id.textViewStop);

        isPlaying = false;
        isThreadRunning = true;
        
        UpdateProgress updateProgress = new UpdateProgress();
        thread = new Thread(updateProgress);
        thread.start();
    }
    
	@Override
	protected void onDestroy() {
		if (DEBUG) Log.d(TAG, "onDestroy()");
		
		isThreadRunning = false;
		if (audio_play != null) {
			try {
				audio_play.stop();
				audio_play.release();
			}
			catch (Exception e) {
				//
			}
		}
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
    			setDuratinSeekBar(audio_play.getDuration());
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
    			afd = getAssets().openFd("number_song.mp3");
    			audio_play.setDataSource(afd.getFileDescriptor(),
    					afd.getStartOffset(), afd.getLength());
    			audio_play.prepare();
    			setDuratinSeekBar(audio_play.getDuration());
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
    		setDuratinSeekBar(audio_play.getDuration());
    		audio_play.start();
    		isPlaying = true;
    		break;
    	}
    }
    
    class UpdateProgress implements Runnable {
		@Override
		public void run() {
			while (isThreadRunning) {
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
    
    public void setDuratinSeekBar(int duration) {
    	int sec;
    	sec = duration / 1000;
    	
    	seekbar.setMax(duration);
    	textviewStop.setText(new Integer(sec).toString());
    }
    
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		if (DEBUG) Log.d(TAG, "onStart()");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		if (DEBUG) Log.d(TAG, "onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (DEBUG) Log.d(TAG, "onResume()");
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (DEBUG) Log.d(TAG, "onPause()");
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (DEBUG) Log.d(TAG, "onStop()");
		super.onStop();
	}
}