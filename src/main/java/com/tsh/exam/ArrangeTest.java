package com.tsh.exam;

import java.util.ArrayList;
import java.util.List;

public class ArrangeTest {

	public static void main(String[] args) {
		String[] chs = {"a","b","c"}; 
		List<String[]> list = new ArrayList<String[]>();
		arrange(chs, 0, chs.length,list); 
		for(String[] _chs : list){
			for(String s : _chs){
				System.out.print(s);
			}
			System.out.println();
		}
	}

	public static void arrange(String[] strs) {
		for (int i = 0; i < strs.length - 2; i++) {

		}
	}

	public static void arrange(String[] chs, int start, int len,List<String[]> list) {
		if (start == len - 1) {
			String[] _chs = new String[chs.length];
			for (int i = 0; i < chs.length; ++i)
				_chs[i] = chs[i];
			list.add(_chs);
//			System.out.println();
			return;
		}
		for (int i = start; i < len; i++) {
			String temp = chs[start];
			chs[start] = chs[i];
			chs[i] = temp;
			arrange(chs, start + 1, len,list);
			temp = chs[start];
			chs[start] = chs[i];
			chs[i] = temp;
		}
	}
	
	public static void arrange(int[] chs, int start, int len,List<int[]> list) {
		if (start == len - 1) {
			int[] _chs = new int[chs.length];
			for (int i = 0; i < chs.length; ++i)
				_chs[i] = chs[i];
			list.add(_chs);
			return;
		}
		for (int i = start; i < len; i++) {
			int temp = chs[start];
			chs[start] = chs[i];
			chs[i] = temp;
			arrange(chs, start + 1, len,list);
			temp = chs[start];
			chs[start] = chs[i];
			chs[i] = temp;
		}
	}
	
}
