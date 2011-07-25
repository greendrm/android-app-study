package greendrm.simple.calculator;

import java.util.LinkedList;

public class HelloSimpleCalculator {
	private final static String EQ = "2+3*4-30/2";
	private final static String OP = "*/+-";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calculate calculate = new Calculate();
		LinkedList<String>stackNumber = new LinkedList<String>();
		LinkedList<String>stackOperator = new LinkedList<String>();
		char[] tmp;;
		int off = 0, len = 0;
		boolean bCal = false;
		
		tmp = EQ.toCharArray();
		for (int i = 0; i < EQ.length(); i++) {
			boolean bEnd = false;
			if (i == (EQ.length() - 1)) {
				bEnd = true;
				len++;
			}
			for (int j = 0; j < OP.length(); j++) {
				if (EQ.charAt(i) == OP.charAt(j) ||	bEnd) {
					stackNumber.addLast(String.valueOf(tmp, off, len));
					if (bCal) {
						String res = null;
						String op = stackOperator.removeLast();
						if (op.equals("*")) {
							res = calculate.Mul(stackNumber.removeLast(), stackNumber.removeLast());
						}
						else if (op.equals("/")) {
							res = calculate.Div(stackNumber.removeLast(), stackNumber.removeLast());
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
					}
					
					break;
				}
			}
			len++;
		}
		
		while(stackOperator.size() > 0) {
			String op = stackOperator.removeLast();
			String res = null;

			if (op.equals("+")) {
				res = calculate.Add(stackNumber.removeLast(), stackNumber.removeLast());
			}
			else if (op.equals("-")) {
				res = calculate.Sub(stackNumber.removeLast(), stackNumber.removeLast());
			}
			if (res != null)
				stackNumber.addLast(res);
		}
		
		System.out.println(EQ + " = " + stackNumber.removeLast());
	}
}
