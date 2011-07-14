package com.greendrm;

public class HelloThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ThreadDigit thread = new ThreadDigit();
//		thread.start();
		ThreadDigit obj = new ThreadDigit();
		Thread thread = new Thread(obj);
		
		ThreadAlpha obj2 = new ThreadAlpha();
		Thread thread2 = new Thread(obj2);
		
		thread.start();
		thread2.start();
	}
}
