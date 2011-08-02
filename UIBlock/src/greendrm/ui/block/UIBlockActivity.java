package greendrm.ui.block;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class UIBlockActivity extends Activity {
	private static final String TAG = "UIBlock";
	private String imgUrl = "http://pds18.egloos.com/pds/201008/31/31/e0019531_4c7c327a2e410.jpg";
    private ImageView mImageView;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mImageView = (ImageView)findViewById(R.id.imageView1);
    }
    
    public void onClickButton(View v) {
    	mImageView.post(new Runnable() {
    	//new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
//				Bitmap bitmap = null;
//				try {
//					bitmap = bitmapFromNetwork(imgUrl);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				mImageView.setImageBitmap(bitmap);
				new DownloadImageTask().execute(imgUrl);
			}
    	});
    	//}).start();
    }
    
    private Bitmap bitmapFromNetwork(String... urls) throws IOException {
    	return BitmapFactory.decodeResource(this.getResources(), R.drawable.android2);
    }
    
    private Bitmap downloadBitmap(String url) {

        // AndroidHttpClient is not allowed to be used from the main thread
        //final HttpClient client = new DefaultHttpClient();
        final HttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    // return BitmapFactory.decodeStream(inputStream);
                    // Bug on slow connections, fixed in future release.
                    return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException e) {
            getRequest.abort();
            Log.w(TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort();
            Log.w(TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort();
            Log.w(TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            if ((client instanceof AndroidHttpClient)) {
                ((AndroidHttpClient) client).close();
            }
        }
        return null;
    }
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	private Bitmap result;
    	
		@Override
		protected Bitmap doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			result = downloadBitmap(arg0[0]);
//			try {
//				result = bitmapFromNetwork(arg0[0]);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return result;
		}
		
		protected void onPostExecute(Bitmap result) {
			mImageView.setImageBitmap(result);
		}
    }
    
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}