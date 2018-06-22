package com.tsh.algorithm.leetcode;

/**
 * 回文数
 * @author zengfa
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。<br \>
 输入: 121
 输出: true<br \>
输入: -121
输出: false
解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。<br \>
输入: 10
输出: false
解释: 从右向左读, 为 01 。因此它不是一个回文数。
 *
 */
public class PalindromeNumber {

	public static void main(String[] args) {
//		int sourceNum = 12321;
//		int newNum = changeNumber(sourceNum);
//		System.out.println(newNum==sourceNum);
		
		String sourceStr = "bb";
		String newStr = changeStr(sourceStr);
		System.out.println(newStr.endsWith(sourceStr));
	}
	
	/**
	 * 转变从右向左的数字
	 * @param number
	 * @return
	 */
	public static int changeNumber(Integer number){
		int result = 0;
		while(number >0){
			int num = number % 10;
			result = result * 10 + num;
			number = number / 10;
		}
		return result;
	}
	
	/**
	 * 转变从右向左的字符串
	 * @param str
	 * @return
	 */
	public static String changeStr(String str){
		char[] s = str.toCharArray();
		for(int i=0,j=s.length-1;i<j;i++,j--){
			char t = s[i];
			s[i] = s[j];
			s[j] = t;
		}
		return new String(s);
		
	}
	
}
