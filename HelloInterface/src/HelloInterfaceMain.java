
public class HelloInterfaceMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SeparateVolume obj1 = new SeparateVolume("1", "삼국지", "아무개");
		AppCDInfo obj2 = new AppCDInfo("2", "빨간마후라");
		
		obj1.checkOut("철수", "20110711");
		obj2.checkOut("영희", "20110712");
		obj1.checkIn();
		obj2.checkIn();
	}

}
