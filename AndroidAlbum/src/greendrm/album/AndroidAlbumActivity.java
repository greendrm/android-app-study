package greendrm.album;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class AndroidAlbumActivity extends Activity implements OnClickListener {
	private static final int PIC_FROM_ALBUM = 1;
	Button buttonAlbum;
	
	private Uri mImageCaptureUri;
	private ImageView mPhotoImageView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonAlbum = (Button)findViewById(R.id.buttonAlbum);
        mPhotoImageView = (ImageView)findViewById(R.id.imageViewPicture);
        
        buttonAlbum.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		DialogInterface.OnClickListener albumListener = 
			new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				takeAlbumAction();
			}
		};
		
		DialogInterface.OnClickListener cancelListener = 
			new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
		
		new AlertDialog.Builder(this)
			.setTitle("Select the picture")
			.setNeutralButton("Select", albumListener)
			.setNegativeButton("Cancle", cancelListener)
			.show();
//		if (v == buttonAlbum) {
//			takeAlbumAction();
//		}
	}
	
	private void takeAlbumAction() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PIC_FROM_ALBUM);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		
		switch(requestCode) {
		case PIC_FROM_ALBUM:
			mImageCaptureUri = data.getData();
			mPhotoImageView.setImageURI(mImageCaptureUri);
		}
	}
	
}