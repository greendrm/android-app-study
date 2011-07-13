package com.greendrm.HelloEnum;

public class HelloEnum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Day[] days = Day.values();
		for (Day day : days) {
			System.out.println(day);
		}
	}
	
	enum Day {
		MONDAY, TUSEDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}

}
