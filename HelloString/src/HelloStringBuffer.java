
public class HelloStringBuffer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer str01 = new StringBuffer();
		
		System.out.println(str01);
		
		str01.append("Java");
		System.out.println(str01);
		
		str01.append(" Programming");
		System.out.println(str01);
		
		str01.replace(0, 4, "MFC"); // [0,4)
		System.out.println(str01);
		
		String str02 = str01.substring(3); // [3,$]
		System.out.println(str02);
		
		str01.deleteCharAt(0);
		System.out.println(str01);
		
		str01.reverse();
		System.out.println(str01);
	}

}
