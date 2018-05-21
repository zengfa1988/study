package com.study.hadoop.rpc;

public class MyProxy implements IProxyProtocol {

	@Override
	public int add(int number1, int number2) {
		System.out.println("我被调用了!");
		int result = number1+number2;
		return result;
	}

}
