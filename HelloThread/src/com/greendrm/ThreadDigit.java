package com.greendrm;

//public class ThreadDigit extends Thread {
//	public void run() {
//		for (int cnt = 0; cnt < 100; cnt++)
//			System.out.print(cnt);
//	}
//}

public class ThreadDigit implements Runnable {
	public void run() {
		for (int cnt = 0; cnt < 100; cnt++) {
			System.out.print(cnt);
			try {
				Thread.sleep(100); // 100 msec
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}