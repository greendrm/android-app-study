class HelloJava10 {
	public static void main(String args[]) {
		int num = 0;
		while(num < 10) {
			for (int i = 0; i < num; i++) {
				System.out.print("*");
			}
			num++;
			System.out.println("");
		}
	}
}