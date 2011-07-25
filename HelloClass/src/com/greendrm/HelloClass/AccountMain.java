package com.greendrm.HelloClass;

public class AccountMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Account account1 = new Account("111-1111-1111", "김도집", 10000000);
		Account account2 = new Account("222-2222-2222", "배용준", 100000000);
		BonusAccount bonus1 = new BonusAccount("333-3333-3333", "김태희", 1000000, 100);
		
		account1.deposit(10000);
		try {
			account2.withdraw(100000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		bonus1.deposit(100);
		
		System.out.println(account1.ownerName + " 예금 잔액:" + account1.getBalance());
		System.out.println(account2.ownerName + " 예금 잔액:" + account2.getBalance());
		System.out.println(bonus1.ownerName + " 예금 잔액:" + bonus1.getBalance() + " 보너스:" + bonus1.getBonus());
	}

}
