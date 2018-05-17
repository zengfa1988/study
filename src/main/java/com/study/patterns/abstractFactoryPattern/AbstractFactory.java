package com.study.patterns.abstractFactoryPattern;

import com.study.patterns.factoryPattern.Shape;

public abstract class AbstractFactory {

	abstract Color getColor(String color);
	
	abstract Shape getShape(String shape);
}
