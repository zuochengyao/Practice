package com.icheero.sdk.knowledge.designpattern.behavioral.strategy;

public class CashRebate extends CashBase
{
	private double moneyRebate;
	
	public CashRebate(double moneyRebate)
	{
		this.moneyRebate = moneyRebate;
	}
	
	@Override
	public double acceptCash(double money)
	{
		return money * moneyRebate;
	}


	
}
