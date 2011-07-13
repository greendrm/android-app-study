package com.greendrm.HelloEnum2;

public class HelloEnum2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printDay("MONDAY");
		printDay("WEDNESDAY");
		printDay("FRIDAY");
	}
	
	static void printDay(String name) {
		Day day = Day.valueOf(name);
		System.out.println(day);
	}
	
	enum Day {
		MONDAY, TUSEDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}
}
