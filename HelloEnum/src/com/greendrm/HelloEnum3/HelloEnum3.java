package com.greendrm.HelloEnum3;

public class HelloEnum3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		printSeason(Season.SPRING);
		printSeason(Season.SUMMER);
		printSeason(Season.WINTER);
	}
	
	static void printSeason(Season season) {
		System.out.println(season.value());
	}
	
	enum Season {
		SPRING("봄"), SUMMER("여름"), FALL("가을"), WINTER("겨울");
		final private String name;
		Season(String name) {
			this.name = name;
		}
		String value() {
			return name;
		}
	}
}
