package greendrm.recording;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.Toast;
import android.widget.Toast;

public class AndroidRecordingActivity extends Activity implements OnClickListener,MediaScannerConnectionClient {
	public static final int REQ_RECORDING = 1;
	private Uri recordAudioPath;
	
	Button buttonRecording, buttonAudio, buttonDelete;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonRecording = (Button)findViewById(R.id.buttonStartRecording);
        buttonAudio = (Button)findViewById(R.id.buttonAudio);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        
        buttonRecording.setOnClickListener(this);
        buttonAudio.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        
        buttonDelete.setVisibility(View.INVISIBLE);
    }
    
    @Override
	public void onClick(View v) {
    	if (v == buttonRecording) {
    		startMediaStoreRecording();
    	}
    	else if (v == buttonAudio) {
    		playAudio();
    	}
    	else if (v == buttonDelete) {
    		deleteAudio();
    	}
	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQ_RECORDING) {
    		if (resultCode == Activity.RESULT_OK) {
    			recordAudioPath = data.getData();
    			buttonAudio.setText(getResources().getString(R.string.audioPlay));
    			buttonDelete.setVisibility(View.VISIBLE);
    		}
    	}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	// MediaScanner
	@Override
	public void onMediaScannerConnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScanCompleted(String arg0, Uri arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void startMediaStoreRecording() {
		Intent intent = new Intent("android.provider.MediaStore.RECORD_SOUND");
		startActivityForResult(intent, REQ_RECORDING);
	}
	
	private void deleteAudio() {
		if (getContentResolver().delete(recordAudioPath, null, null) == 1) {
			Toast.makeText(this, "Sucess to delete", Toast.LENGTH_LONG).show();
			buttonAudio.setText(getResources().getString(R.string.audioNo));
			buttonDelete.setVisibility(View.INVISIBLE);
		}
	}
	
	private void playAudio() {
		Intent intent = new Intent();
		intent.setType("audio/*");
		startActivity(Intent.createChooser(intent, "Select music"));
		
		MediaPlayer audio_play = new MediaPlayer();
		audio_play.setOnCompletionListener(completionListener);
		try {
			audio_play.setDataSource(this, recordAudioPath);
			audio_play.prepare();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		audio_play.start();
	}
	
	private OnCompletionListener completionListener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			buttonAudio.setText("Play Again...");
			mp.release();
		}
	};
}