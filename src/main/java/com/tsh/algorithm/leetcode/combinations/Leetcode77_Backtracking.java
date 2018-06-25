package com.tsh.algorithm.leetcode.combinations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 
 输入: n = 4, k = 2
输出:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]

 * @author zengfa
 *
 */
public class Leetcode77_Backtracking {

	public static void main(String[] args) {
		getCombinations(5, 4);

	}
	
	public static List<Set> getCombinations(int n,int k){
		if(k>n){
			return null;
		}
		List<Set> data = new ArrayList<Set>();
		getSubCom(1, n, null, data, k);
		for(Set s : data){
			Object[] a = s.toArray();
			for(Object o : a){
				System.out.print(o+" ");
			}
			System.out.println();
		}
		return data;
	}
	
	public static void getSubCom(int start ,int end,Set hasData,List<Set> data,int findNum){
		if(findNum==1){
			for(int i=start;i<=end;i++){
				Set set = new HashSet();
				set.add(i);
				set.addAll(hasData);
				data.add(set);
			}
			return;
		}
		for(int i=start;i<=end-findNum+1;i++){
			Set n = new HashSet();
			if(hasData != null){
				n.addAll(hasData);
			}
			n.add(i);
			getSubCom(i+1, end, n, data, findNum-1);
		}
	}

}
