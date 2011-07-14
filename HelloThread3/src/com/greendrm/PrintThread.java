package com.greendrm;

public class PrintThread extends Thread {
	SharedArea sharedArea;
	PrintThread(SharedArea area) {
		sharedArea = area;
	}
	public void run() {
		synchronized (sharedArea) {
			for (int cnt = 0; cnt < 3; cnt++) {
				int sum = sharedArea.account1.getBalance() +
				sharedArea.account2.getBalance();
				System.out.println("계좌 잔액 합계: " + sum);
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
