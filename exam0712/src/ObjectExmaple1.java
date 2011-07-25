
public class ObjectExmaple1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer obj;
		obj = new StringBuffer("Hey, Android");
		obj.deleteCharAt(1);
		obj.deleteCharAt(1);
		obj.insert(1, 'i');
		System.out.println(obj);
	}

}
