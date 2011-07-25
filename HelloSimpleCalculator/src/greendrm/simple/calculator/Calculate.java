package greendrm.simple.calculator;

public class Calculate {
	String Add(String a, String b) {
		Integer res;
		System.out.println(b + " + " + a);
		res = Integer.parseInt(b) + Integer.parseInt(a);
		return res.toString();
	}

	String Sub(String a, String b) {
		Integer res;
		System.out.println(b + " - " + a);
		res = Integer.parseInt(b) - Integer.parseInt(a);
		return res.toString();
	}

	String Mul(String a, String b) {
		Integer res;
		System.out.println(b + " * " + a);
		try {
			res = Integer.parseInt(b) * Integer.parseInt(a);
		}
		catch (NumberFormatException e) {
			return "0";
		}
		return res.toString();
	}

	String Div(String a, String b) {
		Integer res;
		System.out.println(b + " / " + a);
		try {
			res = Integer.parseInt(b) / Integer.parseInt(a);
		}
		catch (NumberFormatException e) {
			return "0";
		}
		return res.toString();
	}
}
