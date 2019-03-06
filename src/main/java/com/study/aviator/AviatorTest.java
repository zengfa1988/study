package com.study.aviator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

/**
 * 运算表达式处理
 * @author Thinkpad
 *
 */
public class AviatorTest {

	public static void main(String[] args) {
		test4();
	}
	
	/**
	 * 变量和字符串相加
	 */
	public static void test1() {
		Map<String,Object> env = new HashMap<String,Object>();
		env.put("name", "admin");
		String str = (String)AviatorEvaluator.execute("'hello:'+name", env);
		System.out.println(str);
		
		str = (String)AviatorEvaluator.exec("'hello:'+name", "jiajia");
		System.out.println(str);
	}
	
	/**
	 * 三元表达式
	 */
	public static void test2() {
		String str = (String)AviatorEvaluator.execute("3>5?'yes':'no'");
		System.out.println(str);
		
		str = (String)AviatorEvaluator.execute("true?'yes':'no'");
		System.out.println(str);
		
		str = (String)AviatorEvaluator.exec("name=='jiajia'?'yes':'no'","admin");
		System.out.println(str);
		
		Map<String,Object> env = new HashMap<String,Object>();
		env.put("name", "admin");
		str = (String)AviatorEvaluator.execute("name=='admin'?'yes':'no'",env);
		System.out.println(str);
	}
	
	/**
	 * 函数调用
	 */
	public static void test3() {
		Long a = (Long)AviatorEvaluator.execute("string.length('hello:admin')");
		System.out.println(a);
		
		a = (Long)AviatorEvaluator.exec("string.length(name)","admin");
		System.out.println(a);
		
		Boolean b = (Boolean)AviatorEvaluator.execute("string.contains('hello','o')");
		System.out.println(b);
		
		Map<String,Object> env = new HashMap<String,Object>();
		env.put("d", new Date());
		String c = (String)AviatorEvaluator.execute("date_to_string(d,'yyyy-MM-dd HH:mm:dd')", env);
		System.out.println(c);
	}
	
	public static void test4() {
		AviatorEvaluator.addFunction(new AddFunction());
		Double c = (Double)AviatorEvaluator.exec("my.sum(2,5)");
		System.out.println(c);
		
		//先编译再计算,提高性能
		Expression compiledExp = AviatorEvaluator.compile("my.sum(a,b)");
		Map<String,Object> env = new HashMap<String,Object>();
		env.put("a", 1);
		env.put("b", 2);
		c = (Double)compiledExp.execute(env);
		System.out.println(c);
	}
}
