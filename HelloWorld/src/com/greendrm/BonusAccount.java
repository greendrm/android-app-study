package com.greendrm;

public class BonusAccount extends Account {
	private int bonusPoint;
	
	BonusAccount(String accountNo, String ownerName, int balance, int bonus) {
		super(accountNo, ownerName, balance);
		bonusPoint = bonus;
	}

	void deposit(int amount)
	{
		bonusPoint += amount;
	}
	
	int withdraw(int amount) {
		bonusPoint -= amount;
		return bonusPoint;
	}
	
	int getBonus() {
		return bonusPoint;
	}
}
