package greendrm.simple.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AndroidSimpleCalculatorActivity extends Activity implements OnClickListener {
	private Button buttonMul, buttonDiv, buttonAdd, buttonSub, buttonDone, buttonClr;
	private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0;
	private EditText edit;
	private String mStr = new String();
	private boolean bOp = false;
	private boolean bMinus = false;
	
	SimpleCalculator calculator = new SimpleCalculator();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonMul = (Button)findViewById(R.id.buttonMul);
        buttonDiv = (Button)findViewById(R.id.buttonDiv);
        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonSub = (Button)findViewById(R.id.buttonSub);
        buttonDone = (Button)findViewById(R.id.buttonDone);
        buttonClr = (Button)findViewById(R.id.buttonClr);
        
        edit = (EditText)findViewById(R.id.editText1);
        
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b5 = (Button)findViewById(R.id.button5);
        b6 = (Button)findViewById(R.id.button6);
        b7 = (Button)findViewById(R.id.button7);
        b8 = (Button)findViewById(R.id.button8);
        b9 = (Button)findViewById(R.id.button9);
        b0 = (Button)findViewById(R.id.button0);
        
        buttonMul.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonDone.setOnClickListener(this);   
        buttonClr.setOnClickListener(this);
        
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			mStr += "1";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button2:
			mStr += "2";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button3:
			mStr += "3";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button4:
			mStr += "4";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button5:
			mStr += "5";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button6:
			mStr += "6";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button7:
			mStr += "7";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button8:
			mStr += "8";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button9:
			mStr += "9";
			bOp = false;
			bMinus = false;
			break;
		case R.id.button0:
			mStr += "0";
			bOp = false;
			bMinus = false;
			break;
		case R.id.buttonMul:
			if (bOp == false) {
				mStr += "*";
				bOp = true;
			}
			break;
		case R.id.buttonDiv:
			if (bOp == false) {
				mStr += "/";
				bOp = true;
			}
			break;
		case R.id.buttonAdd:
			if (bOp == false) {
				mStr += "+";
				bOp = true;
			}
			break;
		case R.id.buttonSub:
			if (bOp == false) {
				mStr += "-";
				bOp = true;
			}
			else {
				if (bMinus == false) {
					mStr += "-";
					bMinus = true;
				}
			}
			break;
		case R.id.buttonDone:
			mStr = calculator.calc(mStr);
			bOp = bMinus = false;
			break;
		case R.id.buttonClr:
			mStr = "";
			bOp = bMinus = false;
			break;
		}
		
		edit.setText(mStr);
	}
}