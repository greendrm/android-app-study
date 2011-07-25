package greendrm.simple.calculator;

import java.util.LinkedList;
import android.util.Log;

public class SimpleCalculator {
	private final String TAG = "DJK";
	private String mstrEquation;
	private final String OP = "*/+-";
	
	private LinkedList<String>stackNumber = new LinkedList<String>();
	private LinkedList<String>stackOperator = new LinkedList<String>();
	
	public String calc(String str) {
		Calculate calculate = new Calculate();
		char[] tmp;;
		int off = 0, len = 0;
		boolean bCal = false;
		boolean bEnd = false;
		boolean bFirst = true;
		
		mstrEquation = str;
		flushStack();
		
		tmp = mstrEquation.toCharArray();
		for (int i = 0; i < mstrEquation.length(); i++) {

			if (i == (mstrEquation.length() - 1)) {
				bEnd = true;
				len++;
			}
			for (int j = 0; j < OP.length(); j++) {
				if (bFirst && mstrEquation.charAt(i) == '-')
					break;
				
				if (mstrEquation.charAt(i) == OP.charAt(j) ||	bEnd) {
					stackNumber.addLast(String.valueOf(tmp, off, len));
					bFirst = true;
					
					if (bCal) {
						String res = null;
						String op = stackOperator.removeLast();
						
						if (op.equals("*")) {
							String a = stackNumber.removeLast();
							String b = stackNumber.removeLast();
							res = calculate.Mul(b, a);
							if (isError(res))
								return res;
						}
						else if (op.equals("/")) {
							String a = stackNumber.removeLast();
							String b = stackNumber.removeLast();
							res = calculate.Div(b, a);
							if (isError(res))
								return res;
						}
						if (res != null)
							stackNumber.addLast(res);
						bCal = false;
					}
					if (bEnd == false) {
						stackOperator.addLast(String.valueOf(OP.charAt(j)));
						off = i+1;
						len = -1;
						if (OP.charAt(j)== '*' || OP.charAt(j) == '/') {
							bCal = true;
						}
						
						if (mstrEquation.charAt(i+1) == '-'){
							Log.d(TAG, ""+mstrEquation.charAt(i) + mstrEquation.charAt(i+1));
							i++;
							len++;
						}
					}
					
					break;
				}
			}
			len++;
			bFirst = false;
		}
		
		while(stackOperator.size() > 0) {
			String op;
			String a;
			String b;
			String res = null;
			
			op = stackOperator.poll();
			
			a = stackNumber.poll(); // first element
			b = stackNumber.poll();

			if (op.equals("+")) {
				res = calculate.Add(a, b);
				if (isError(res))
					return res;
			}
			else if (op.equals("-")) {
				res = calculate.Sub(a, b);
				if (isError(res))
					return res;
			}
			if (res != null)
				stackNumber.addFirst(res);
		}
		
		return stackNumber.removeLast();
	}
	
	private boolean isError(String res) {
		if (res.equals(Calculate.ERROR)) {
			flushStack();
			return true;
		}
		else {
			return false;
		}
	}
	
	private void flushStack(LinkedList<String> stack) {
		while(stack.size() > 0)
			stack.removeLast();
	}
	
	private void flushStack() {
		flushStack(stackNumber);
		flushStack(stackOperator);
	}
}
