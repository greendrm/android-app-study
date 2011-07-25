
public class BreakTest {
	public static void main(String[] args) {
		int[] arr = { 435, 88, 67, 32, 88, -1, 6, 12, 7, 8, 45, 11};
		for (int cnt = 0; cnt < arr.length; cnt++) {
			if (arr[cnt] == -1)
				break;
			System.out.println(arr[cnt]);
		}
	}
}
