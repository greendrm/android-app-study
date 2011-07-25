
public class HelloUnicode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int cnt = 0;
		for (char c = 'ㄱ'; c < 'ㅣ'; c++) {
			System.out.print(c);
			if ((cnt+1) % 8 == 0)
				System.out.println();
			cnt++;
		}
	}
}
