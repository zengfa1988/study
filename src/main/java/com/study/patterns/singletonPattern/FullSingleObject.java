package com.study.patterns.singletonPattern;

/**
 * 饱汉单例模式
 * @author zengfa
 *
 */
public class FullSingleObject {

	// create an object of SingleObject
	private static FullSingleObject instance = new FullSingleObject();

	// make the constructor private so that this class cannot be
	// instantiated
	private FullSingleObject() {
	}

	// Get the only object available
	public static FullSingleObject getInstance() {
		return instance;
	}

	public void showMessage() {
		System.out.println("Hello World!");
	}
}
