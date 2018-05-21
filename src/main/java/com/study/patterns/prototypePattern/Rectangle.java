package com.study.patterns.prototypePattern;

public class Rectangle extends Shape {

	public Rectangle(){
		type = "Rectangle";
	}
	
	@Override
	void draw() {
		System.out.println("Inside Rectangle::draw() method.");
	}

}
