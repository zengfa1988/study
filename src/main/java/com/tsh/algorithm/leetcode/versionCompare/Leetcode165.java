package com.tsh.algorithm.leetcode.versionCompare;

public class Leetcode165 {

	public static void main(String[] args) {
		int r = compareVersion("7.5.2.4","7.5.2.4");
		System.out.println(r);
	}
	
	public static int compareVersion(String version1,String version2){
		String[] v1Array = version1.split("\\.");
		String[] v2Array = version2.split("\\.");
		int length = v1Array.length>v2Array.length ? v2Array.length : v1Array.length;
		for(int i=0;i<length;i++){
			Integer num1 = Integer.parseInt(v1Array[i]);
			Integer num2 = Integer.parseInt(v2Array[i]);
			if(num1 > num2){
				return 1;
			}
			if(num1 < num2){
				return -1;
			}
		}
		if(v1Array.length > v2Array.length){
			return 1;
		}else if(v1Array.length < v2Array.length){
			return -1;
		}
		return 0;
	}
}
