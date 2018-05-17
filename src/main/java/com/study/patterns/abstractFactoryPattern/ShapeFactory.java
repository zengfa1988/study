package com.study.patterns.abstractFactoryPattern;

import com.study.patterns.factoryPattern.Circle;
import com.study.patterns.factoryPattern.Rectangle;
import com.study.patterns.factoryPattern.Shape;
import com.study.patterns.factoryPattern.Square;

public class ShapeFactory extends AbstractFactory {

	@Override
	Color getColor(String color) {
		return null;
	}

	@Override
	Shape getShape(String shapeType) {
		if (shapeType == null) {
			return null;
		}

		if (shapeType.equalsIgnoreCase("CIRCLE")) {
			return new Circle();

		} else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
			return new Rectangle();

		} else if (shapeType.equalsIgnoreCase("SQUARE")) {
			return new Square();
		}

		return null;
	}

}
