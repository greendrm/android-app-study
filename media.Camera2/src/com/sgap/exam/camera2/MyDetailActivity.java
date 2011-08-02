package com.sgap.exam.camera2;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

public class MyDetailActivity extends Activity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        
        setContentView(R.layout.main_detail);
    }
}
