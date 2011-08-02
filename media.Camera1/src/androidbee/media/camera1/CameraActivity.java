package androidbee.media.camera1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CameraActivity extends Activity {
	private static final String TAG = "androidbee";
	CameraPreview preview;
	Button buttonClick;

	ImageView imageview;
    File mCameraFile = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.setContentView(R.layout.main);

		preview = new CameraPreview(this);

		imageview = (ImageView) findViewById(R.id.picview);

		((FrameLayout) findViewById(R.id.preview)).addView(preview);
		
		buttonClick = (Button) findViewById(R.id.buttonClick);
		buttonClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "onClick - jpeg");
				preview.mCamera.takePicture(shutterCallback, rawCallback,
						jpegCallback);
			}
		});

	}

	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			FileInputStream inputStream = null;
			long fileNamePrefix = System.currentTimeMillis();
			try {
			    File sdcard = Environment.getExternalStorageDirectory();
	             mCameraFile = File.createTempFile( "tempCamera_"+fileNamePrefix, "_jpg_", sdcard);

//				outStream = new FileOutputStream(String.format(
//						"/sdcard/%d.jpg", fileNamePrefix));
				outStream = new FileOutputStream(mCameraFile);
				outStream.write(data);
				outStream.close();
//				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);

				// mCameraFile = new File(String.format("/sdcard/%d.jpg", fileNamePrefix));
				try {
					inputStream = new FileInputStream(mCameraFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				preview.setVisibility(View.GONE);
				Bitmap bi;
				bi = BitmapFactory.decodeStream(inputStream, null, null);
				imageview.setImageBitmap(bi);

				inputStream.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}

			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};

	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};
	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	
	
}
