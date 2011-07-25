package com.greendrm.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


public class HelloView extends View {
	public HelloView(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawText("Hello World", 0, 25, new Paint());
	}
}
