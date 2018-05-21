package com.study.hadoop.rpc;

/**
 * 参考https://my.oschina.net/u/2503731/blog/661216
 * @author zengfa
 *
 */
public class MyProxy implements IProxyProtocol {

	@Override
	public int add(int number1, int number2) {
		System.out.println("我被调用了!");
		int result = number1+number2;
		return result;
	}

}
