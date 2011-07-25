
public class HelloOperation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = 3, b = 2;
		int result;
		result = b++ + --a * b++; // 8
		System.out.println("result = " + result);
	}

}
