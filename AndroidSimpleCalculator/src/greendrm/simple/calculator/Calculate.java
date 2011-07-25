package greendrm.simple.calculator;

public class Calculate {
	public static final String ERROR="error";
	
	String Add(String a, String b) {
		Integer res;
		try {
			System.out.println(a + " + " + b);
			res = Integer.parseInt(a) + Integer.parseInt(b);
		}
		catch (NumberFormatException e) {
			return ERROR;
		}
		return res.toString();
	}

	String Sub(String a, String b) {
		Integer res;
		try {
			System.out.println(a + " - " + b);
			res = Integer.parseInt(a) - Integer.parseInt(b);
		}
		catch (NumberFormatException e) {
			return ERROR;
		}
		return res.toString();
	}

	String Mul(String a, String b) {
		Integer res;
		System.out.println(a + " * " + b);
		try {
			res = Integer.parseInt(a) * Integer.parseInt(b);
		}
		catch (NumberFormatException e) {
			return ERROR;
		}
		return res.toString();
	}

	String Div(String a, String b) {
		Integer res;
		System.out.println(a + " / " + b);
		try {
			res = Integer.parseInt(a) / Integer.parseInt(b);
		}
		catch (NumberFormatException e) {
			return ERROR;
		}
		catch (ArithmeticException e) {
			return ERROR;
		}
		return res.toString();
	}
}
