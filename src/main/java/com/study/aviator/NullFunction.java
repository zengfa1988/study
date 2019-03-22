package com.study.aviator;

import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

public class NullFunction extends AbstractFunction{

	@Override
	public String getName() {
		return "isNull";
	}

	@Override
	public AviatorObject call(Map<String, Object> env, AviatorObject arg) {
		Object o = FunctionUtils.getJavaObject(arg, env);
		return o == null?AviatorBoolean.TRUE:AviatorBoolean.FALSE;
	}

}
