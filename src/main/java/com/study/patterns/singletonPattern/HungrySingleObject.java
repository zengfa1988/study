package com.study.patterns.singletonPattern;

/**
 * 饿汉单例模式
 * @author zengfa
 *
 */
public class HungrySingleObject {

	// create an object of SingleObject
	private static HungrySingleObject instance;

	// make the constructor private so that this class cannot be
	// instantiated
	private HungrySingleObject() {
	}

	// Get the only object available
	public static HungrySingleObject getInstance() {
		if (instance == null) {  
            instance = new HungrySingleObject();  
        }
		return instance;
	}

	public void showMessage() {
		System.out.println("Hello World!");
	}
}
