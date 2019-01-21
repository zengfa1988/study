package com.study.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表达式处理
 * @author Thinkpad
 *
 */
public class ExpUtil {

	/**
	 * 自定义表达式处理
	 * @param args
	 */
	public static void main(String[] args) {
		String express = "abc [%repField(type)%]==2 || [%repField(level)%]>5";
		
		String regex = "(\\[%[^\\]]*%\\])";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(express);
		StringBuffer sb = new StringBuffer();
		int sPoint = 0;
		int ePoint = 0;
		int i = 0;
		while(m.find()) {
			ePoint = m.start();
			sb.append(express.substring(sPoint, ePoint));
			sPoint = m.end();
			sb.append("|"+i+"|");
			i++;
		}
		sb.append(express.substring(sPoint, express.length()));
		System.out.println(sb.toString());
	}
}
