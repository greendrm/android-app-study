package com.sgap.exam.camera2;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path.FillType;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

// Camera의 SurfaceView를 가지고 논다..

public class CameraActivity extends Activity {
    private static final String TAG = "EXAMPLE===> ";
    private Display mDisplay;
    private static int DISPLAY_WITH;
    private static int DISPLAY_HEIGHT;
    
    private Camera camera;
    private SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
    
    private Uri targetResource = Media.EXTERNAL_CONTENT_URI;
    private CameraPreview mView;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private DrawOnTop mOverlay;
    private Drawable mDrawable;
    private Resources mRes;

    public void onCreate(Bundle icicle) {
        //TAG =         getClass().getSimpleName();
        Log.d( TAG, "onCreate");
        mDisplay = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DISPLAY_WITH = mDisplay.getWidth();
        DISPLAY_HEIGHT = mDisplay.getHeight();
        
        LayoutInflater inflater = getLayoutInflater();
        
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mRes = getResources();
        
        setContentView(R.layout.main);
        RelativeLayout container = (RelativeLayout)findViewById(R.id.container);
        
        mView =  new CameraPreview(this);
        mView.getHolder().setFormat(PixelFormat.TRANSLUCENT); 

        //mOverlay = (DrawOnTop)this.findViewById(R.id.overlay);
        mOverlay = new DrawOnTop(this);
        
        //[[ 버튼 추가...
        ImageView btn_list = new ImageView(this);
        btn_list.setBackgroundResource(R.drawable.selector_btn_list);
        
        View view1 = inflater.inflate(R.layout.arview_overlaybutton, null);
        View view2 = inflater.inflate(R.layout.arview_overlaybutton, null);
        ImageView btn2 = (ImageView)view2.findViewById(R.id.btn_icon);
        btn2.setBackgroundResource(R.drawable.ar_view_icon02);
        btn2.setOnClickListener(detailClickListener);
        ImageView btn22 = (ImageView)view2.findViewById(R.id.btn_icon2);
        btn22.setBackgroundResource(R.drawable.selector_cate02);
        btn22.setOnClickListener(detailClickListener);
        ImageView btn23 = (ImageView)view2.findViewById(R.id.btn_icon3);
        btn23.setBackgroundResource(R.drawable.selector_btn_del);
        
        View view3 = inflater.inflate(R.layout.arview_overlaybutton, null);
        ImageView btn3 = (ImageView)view3.findViewById(R.id.btn_icon);
        btn3.setBackgroundResource(R.drawable.ar_view_icon03);
        btn3.setOnClickListener(detailClickListener);
        ImageView btn32 = (ImageView)view3.findViewById(R.id.btn_icon2);
        btn32.setBackgroundResource(R.drawable.selector_cate03);
        btn32.setOnClickListener(detailClickListener);
        ImageView btn33 = (ImageView)view3.findViewById(R.id.btn_icon3);
        btn33.setBackgroundResource(R.drawable.selector_btn_del);
 
        View view4 = inflater.inflate(R.layout.arview_overlaybutton, null);
        ImageView btn4 = (ImageView)view4.findViewById(R.id.btn_icon);
        btn4.setBackgroundResource(R.drawable.ar_view_icon04);
        btn4.setOnClickListener(detailClickListener);
        ImageView btn42 = (ImageView)view4.findViewById(R.id.btn_icon2);
        btn42.setBackgroundResource(R.drawable.selector_cate04);
        btn42.setOnClickListener(detailClickListener);
        ImageView btn43 = (ImageView)view4.findViewById(R.id.btn_icon3);
        btn43.setBackgroundResource(R.drawable.selector_btn_del);

        View view5 = inflater.inflate(R.layout.arview_overlaybutton, null);
        ImageView btn5 = (ImageView)view5.findViewById(R.id.btn_icon);
        btn5.setBackgroundResource(R.drawable.ar_view_icon05);
        btn5.setOnClickListener(detailClickListener);
        ImageView btn52 = (ImageView)view5.findViewById(R.id.btn_icon2);
        btn52.setBackgroundResource(R.drawable.selector_cate05);
        btn52.setOnClickListener(detailClickListener);
        ImageView btn53 = (ImageView)view5.findViewById(R.id.btn_icon3);
        btn53.setBackgroundResource(R.drawable.selector_btn_del);

        View view6 = inflater.inflate(R.layout.arview_overlaybutton, null);
        ImageView btn6 = (ImageView)view6.findViewById(R.id.btn_icon);
        btn6.setBackgroundResource(R.drawable.ar_view_icon06);
        btn6.setOnClickListener(detailClickListener);
        btn6.setTag("의류");
        ImageView btn62 = (ImageView)view6.findViewById(R.id.btn_icon2);
        btn62.setBackgroundResource(R.drawable.selector_cate06);
        btn62.setOnClickListener(detailClickListener);
        ImageView btn63 = (ImageView)view6.findViewById(R.id.btn_icon3);
        btn63.setBackgroundResource(R.drawable.selector_btn_del);
        
       
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams param5 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams param6 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        param.setMargins(10, 60, 0, 0);
        param1.setMargins(50, 130, 0, 0);
        param2.setMargins(20, 190, 0, 0);
        param3.setMargins(90, 230, 0, 0);
        param4.setMargins(130, 300, 0, 0);
        param5.setMargins(160, 420, 0, 0);
        param6.setMargins(20, 360, 0, 0);
        
        
        container.addView(btn_list, param);
        container.addView(view1, param1);
        container.addView(view2, param2);
        container.addView(view3, param3);
        container.addView(view4, param4);
        container.addView(view5, param5);
        container.addView(view6, param6);
        //
        addContentView(mOverlay, new LayoutParams( 
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        mSurfaceView = (SurfaceView)this.findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mView);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        /*Button openSound = (Button)findViewById(R.id.openSound);
        openSound.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getBaseContext(), SoundRecordingSample.class);
                //startActivity(intent);
            }
        });*/
        
        Button btn_camera = (Button)findViewById(R.id.btn_camera);
        btn_camera.setEnabled(false);
        
        Button btn_map = (Button)findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v)  {
                Intent intent = new Intent(getBaseContext(), MyMapViewActivity.class);
//                intent.putExtra("time", astrTime);
//                intent.putExtra("lat", astrLat);
//                intent.putExtra("lon", astrLon);
                startActivity(intent);
             }
         });    
    }

    private OnClickListener detailClickListener = new View.OnClickListener() {
        public void onClick(View v)  {
           Intent intent = new Intent(getBaseContext(), MyDetailActivity.class);
           startActivity(intent);
        }
    };
    
    
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuItem item = menu.add(0, 0, 0, "View Pictures");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_VIEW, targetResource);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }
    
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    
   
    Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera c) {
            camera.startPreview();
        }
    };
    

    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
    	public void onShutter() {
    	}
    };
    

    protected void onResume() {
        Log.e(getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState)  {
        super.onSaveInstanceState(outState);
    }

    protected void onStop()  {
    	super.onStop();
    }

    
    public class DrawOnTop extends View {
        String str = "Surface위에 그립니다.";

        int v_width, v_height;
        private int new_X = 0;
        private int new_Y = 0;

        private boolean drawing;

        private float radius;

        private float initX;

        private float initY;
        
        public DrawOnTop(Context context) {
                super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            paint.setTextSize(20);
            canvas.drawText("Surface위에 그립니다.", 10, 10, paint);

        }
        
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                initX = event.getX();
                initY = event.getY();
                radius = 1;
                drawing = true; 
                break;
            case MotionEvent.ACTION_MOVE :
                float x = event.getX();
                float y = event.getY();
                radius = (float) Math.sqrt(Math.pow(x-initX, 2) + Math.pow(y-initY, 2));
                return true;
            case MotionEvent.ACTION_UP :
                updateDrawableXY(event.getX(), event.getY());
                drawing = false; 
                return true;
            }
            
            invalidate();
            return false;
        }
        

        private void updateDrawableXY(float _x, float _y){
            new_X = (int)_x;// + moving_X;
            new_Y = (int)_y;// + moving_Y;
        }
    }
    
    

}