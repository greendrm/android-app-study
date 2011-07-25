package com.greendrm.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


public class HelloView extends View {
	public float m_x = 10.0f, m_y = 10.0f;
	private int color = Color.BLACK;
	
	public HelloView(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
	}
	
	public void setPos(float x, float y) {
		m_x = x;
		m_y = y;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setTextSize(25.0f);
		paint.setColor(-1 - color);
		canvas.drawCircle(m_x, m_y, 32, paint);
		paint.setColor(color);
		canvas.drawText("Hello World", m_x, m_y, paint);
	}
}
