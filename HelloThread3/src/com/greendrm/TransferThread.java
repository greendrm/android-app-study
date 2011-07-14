package com.greendrm;

public class TransferThread extends Thread {
	SharedArea sharedArea;
	TransferThread(SharedArea area) {
		sharedArea = area;
	}
	public void run() {
		for (int cnt = 0; cnt < 12; cnt++) {
			synchronized (sharedArea) {
				try {
					sharedArea.account1.withdraw(100000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print("이몽룡 계좌: 10만원 인출,");
				sharedArea.account2.deposit(100000);
				System.out.println("성춘향 계좌: 10만원 입금");
			}
		}
	}
}
