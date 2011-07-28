
package androidbee.view.custom.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class CustomTextView extends View {

    private Paint linePaint;

    float mFontSize;

    String mText;

    private Typeface mType;

    
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 초기 설정 변수들을 정의한다.
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFF0000FF);

//        mFontSize = getTextSize();
//        mText = getText().toString();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        
        // TextView 기본 클래스를 사용해 평소처럼 테스트를 랜더링한다.
        // TextView의 배경색을 칠한다.
        canvas.drawColor( 0xAAFFFF99);

        // TextView의 세로 괘선을 그린다.
        canvas.drawLine( 20, 0, 20, 100, linePaint);

        // Text를 여백 바로 다음부터 출력되도록 이동시킨다.
        canvas.save();
        canvas.translate(20, 0);

        super.onDraw(canvas);
        // 텍스트 앞에 나타낼 것들을 캔버스에 그린다.
        canvas.restore();
    }
    	

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
