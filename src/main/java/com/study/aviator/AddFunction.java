package com.study.aviator;

import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

public class AddFunction extends AbstractFunction{

	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
		System.out.println("cal my add function");
		Number a = FunctionUtils.getNumberValue(arg1, env);
		Number b = FunctionUtils.getNumberValue(arg2, env);
		return new AviatorDouble(a.doubleValue()+b.doubleValue());
	}

	@Override
	public String getName() {
		return "my.sum";
	}

}
