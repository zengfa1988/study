package com.study.desensitization;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {

	public static void main(String[] args) {
//		String str = "431003198804163515";
//		str = idEncrypt(str);
//		System.out.println(str);
		
		String idCard = "123";
        //$1、$2、……表示正则表达式里面第一个、第二个、……括号里面的匹配内容
        String idCardNumber = idCard.replaceAll("\\w","*");
        System.out.println("身份证号长度："+idCard.length());
        System.out.println("正则idCard中4*：" + idCardNumber);
        
        
        String s = new String(new char[15]).replace("\0", "*");
        System.out.println(s);
        char[] c = new char[2];
        System.out.println(new String(c));
	}
	
	/**
	 * 手机号码前三后四脱敏
	 * 
	 * @param mobile
	 * @return
	 */
	public static String mobileEncrypt(String mobile) {
		if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
			return mobile;
		}
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * 身份证前三后四脱敏
	 * 
	 * @param id
	 * @return
	 */
	public static String idEncrypt(String id) {
		if (StringUtils.isEmpty(id) || (id.length() < 8)) {
			return id;
		}
		return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
	}

	/**
	 * 护照前2后3位脱敏，护照一般为8或9位
	 * @param id
	 * @return
	 */
	public static String idPassport(String id) {
		if (StringUtils.isEmpty(id) || (id.length() < 8)) {
			return id;
		}
		return id.substring(0, 2) + new String(new char[id.length() - 5]).replace("\0", "*")
				+ id.substring(id.length() - 3);
	}
}
